package com.skillswap.server.services.impl;

import com.skillswap.server.dto.request.PaymentProcessRequest;
import com.skillswap.server.dto.response.MembershipDTO;
import com.skillswap.server.dto.response.MembershipSubscriptionDTO;
import com.skillswap.server.entities.Membership;
import com.skillswap.server.entities.MembershipSubscription;
import com.skillswap.server.entities.User;
import com.skillswap.server.enums.MembershipSubscriptionStatus;
import com.skillswap.server.enums.PaymentStatus;
import com.skillswap.server.mapper.MembershipMapper;
import com.skillswap.server.mapper.MembershipSubscriptionMapper;
import com.skillswap.server.repositories.MembershipRepository;
import com.skillswap.server.repositories.MembershipSubscriptionRepository;
import com.skillswap.server.services.MembershipService;
import com.skillswap.server.services.UserService;
import com.skillswap.server.specification.MembershipSpecification;
import com.skillswap.server.specification.MembershipSubscriptionSpecification;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import vn.payos.PayOS;
import vn.payos.type.CheckoutResponseData;
import vn.payos.type.ItemData;
import vn.payos.type.PaymentData;
import vn.payos.type.PaymentLinkData;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MembershipServiceImpl implements MembershipService {

    private final MembershipRepository membershipRepository;
    private final MembershipMapper membershipMapper;
    private final MembershipSubscriptionMapper membershipSubscriptionMapper;
    private final MembershipSubscriptionRepository membershipSubscriptionRepository;
    private final UserService userService;
    private final PayOS payOS;

    @NonFinal
    @Value("${client.url}")
    private String clientUrl;

    @Override
    public MembershipDTO createMembership(MembershipDTO membershipDTO) {
        Membership membership = new Membership();
        membership.setName(membershipDTO.getName());
        membership.setDescription(membershipDTO.getDescription());
        membership.setPrice(membershipDTO.getPrice());
        membership.setDuration(membershipDTO.getDuration());
        membership.setFeatures(membershipDTO.getFeatures());
        Membership savedMembership = membershipRepository.save(membership);

        return membershipMapper.toMembershipDTO(savedMembership);
    }

    @Override
    public List<MembershipDTO> getAllMemberships() {
        List<Membership> memberships = membershipRepository.findAllByIsDeletedFalse();
        return memberships.stream().map(membership -> {
            boolean isBought = membershipSubscriptionRepository
                    .findByUserIdAndMembershipIdAndStatus(userService.getAuthenticatedUser().getId(), membership.getId(), MembershipSubscriptionStatus.ACTIVE)
                    .isPresent();
            return membershipMapper.toMembershipDTO(membership, isBought);
        }).collect(Collectors.toList());
    }

    @Override
    public CheckoutResponseData createPayment(int membershipId) throws Exception {
        String currentTimeString = String.valueOf(new Date().getTime());
        long orderCode = Long.parseLong(currentTimeString.substring(currentTimeString.length() - 6));

        User user = userService.getAuthenticatedUser();

        Membership membership = membershipRepository.findById(membershipId)
                .orElseThrow(() -> new RuntimeException("Membership not found"));

        if (membershipSubscriptionRepository.findByUserIdAndStatus(user.getId(), MembershipSubscriptionStatus.ACTIVE).isPresent()) {
            MembershipSubscription oldSubscription = membershipSubscriptionRepository
                    .findByUserIdAndStatus(user.getId(), MembershipSubscriptionStatus.ACTIVE)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy gói thành viên đang hoạt động cho người dùng: " + user.getId()));
            oldSubscription.setStatus(MembershipSubscriptionStatus.REMOVED);
            oldSubscription.setUpdated(true);
            membershipSubscriptionRepository.save(oldSubscription);
        }

        MembershipSubscription subscription = new MembershipSubscription();
        subscription.setUser(user);
        subscription.setMembership(membership);
        subscription.setStatus(MembershipSubscriptionStatus.INACTIVE);
        subscription.setPaymentStatus(PaymentStatus.PENDING);
        subscription.setStartDate(null);
        subscription.setEndDate(null);
        subscription.setOrderCode(orderCode);
        membershipSubscriptionRepository.save(subscription);
        //PayOS

        String name = "Gói thành viên SkillSwap - " + membership.getName();
        String desc =  "SSM"+orderCode;
        String returnUrl = clientUrl + "/payment/callback";
        String cancelUrl = clientUrl + "/payment/callback";

        ItemData item = ItemData.builder()
                .name(name)
                .quantity(1)
                .price(Integer.valueOf(membership.getPrice().intValue()))
                .build();

        PaymentData data = PaymentData.builder()
                .orderCode(orderCode)
                .description(desc)
                .amount(Integer.valueOf(membership.getPrice().intValue()))
                .returnUrl(returnUrl)
                .cancelUrl(cancelUrl)
                .item(item)
                .build();

        CheckoutResponseData response = payOS.createPaymentLink(data);
        return response;
    }

    @Override
    public MembershipSubscriptionDTO processPayment(PaymentProcessRequest request) {
        User user = userService.getAuthenticatedUser();

        MembershipSubscription subscription = membershipSubscriptionRepository
                .findByOrderCode(request.getOrderCode()).orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng với mã: " + request.getOrderCode()));
        if (!request.isCancel() && request.getStatus().equals(PaymentStatus.PAID)) {

            subscription.setStatus(MembershipSubscriptionStatus.ACTIVE);
            subscription.setStartDate(LocalDateTime.now());
            subscription.setEndDate(LocalDateTime.now().plus(subscription.getMembership().getDuration(), ChronoUnit.DAYS));
            subscription.setPaymentStatus(PaymentStatus.COMPLETED);
            membershipSubscriptionRepository.save(subscription);

            MembershipSubscription oldSubscription = membershipSubscriptionRepository
                    .findByUserIdAndStatusAndIsUpdatedTrue(user.getId(), MembershipSubscriptionStatus.REMOVED)
                    .orElse(null);
            if (oldSubscription != null) {
                oldSubscription.setStatus(MembershipSubscriptionStatus.EXPIRED);
                membershipSubscriptionRepository.save(oldSubscription);
            }


        } else if (request.isCancel() && request.getStatus().equals(PaymentStatus.CANCELLED)) {
            MembershipSubscription oldSubscription = membershipSubscriptionRepository
                    .findByUserIdAndStatusAndIsUpdatedTrue(user.getId(), MembershipSubscriptionStatus.REMOVED)
                    .orElse(null);
            if(oldSubscription != null){
                oldSubscription.setStatus(MembershipSubscriptionStatus.ACTIVE);
                oldSubscription.setUpdated(false);
                membershipSubscriptionRepository.save(oldSubscription);
            } else {
                subscription.setStatus(MembershipSubscriptionStatus.INACTIVE);
                subscription.setUpdated(true);
                membershipSubscriptionRepository.save(subscription);
            }
        }
        return membershipSubscriptionMapper.toMembershipSubscriptionDTO(subscription);
    }

    @Override
    public MembershipSubscriptionDTO getUserMembershipSubscription() {
    User user = userService.getAuthenticatedUser();
    MembershipSubscription subscription = membershipSubscriptionRepository
            .findByUserIdAndStatus(user.getId(), MembershipSubscriptionStatus.ACTIVE)
            .orElse(null);
        return membershipSubscriptionMapper.toMembershipSubscriptionDTO(subscription);
    }

    @Scheduled(fixedRate = 60000 * 60 * 24)
    private void checkMembershipSubscription(){
        List<MembershipSubscription> activeSubscriptions = membershipSubscriptionRepository.findByStatus(MembershipSubscriptionStatus.ACTIVE);
        LocalDateTime now = LocalDateTime.now();
        for(MembershipSubscription subscription : activeSubscriptions){
            if(subscription.getEndDate().isBefore(now)){
                subscription.setStatus(MembershipSubscriptionStatus.EXPIRED);
                membershipSubscriptionRepository.save(subscription);
            }
        }
    }

    @Override
    public Page<Membership> getAllMembershipsForAdmin(
            int page,
            int size,
            Sort.Direction sort,
            String searchString,
            boolean status
    ) {
        Specification<Membership> spec = Specification.allOf(MembershipSpecification.hasSearchString(searchString).and(MembershipSpecification.hasStatus(status)));
        Pageable pageable = PageRequest.of(page, size, sort, "createdAt");

       return membershipRepository.findAll(spec, pageable);
    }

    @Override
    public PaymentLinkData cancelPayment(long orderCode) throws Exception {
        MembershipSubscription subscription = membershipSubscriptionRepository.findByOrderCode(orderCode)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng với mã: " + orderCode));
        subscription.setPaymentStatus(PaymentStatus.CANCELLED);
        membershipSubscriptionRepository.save(subscription);
        return payOS.cancelPaymentLink(orderCode, "Hủy đơn hàng với mã: " + orderCode);

    }

    @Override
    public MembershipSubscription getValidMembershipSubscription(int userId) {
        return membershipSubscriptionRepository.findByUserIdAndStatus(userId, MembershipSubscriptionStatus.ACTIVE)
                .orElse(null);
    }

    @Override
    public void deleteMembership(int membershipId) {
        Membership membership = membershipRepository.findById(membershipId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy membership với ID: " + membershipId));
        membership.setDeleted(true);
        membershipRepository.save(membership);
    }

    @Override
    public MembershipDTO updateMembership(int membershipId, MembershipDTO membershipDTO) {
        Membership membership = membershipRepository.findById(membershipId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy membership với ID: " + membershipId));
        membership.setName(membershipDTO.getName());
        membership.setDescription(membershipDTO.getDescription());
        membership.setPrice(membershipDTO.getPrice());
        membership.setDuration(membershipDTO.getDuration());
        membership.setFeatures(membershipDTO.getFeatures());
        Membership updatedMembership = membershipRepository.save(membership);
        return membershipMapper.toMembershipDTO(updatedMembership);
    }

    @Override
    public Page<MembershipSubscriptionDTO> getMembershipSubscriptions(int page, int size,String searchString, Sort.Direction sort, MembershipSubscriptionStatus status, PaymentStatus paymentStatus) {
        Pageable pageable = PageRequest.of(page, size, sort, "createdAt");
        String statusString = status != null ? status.name() : "";
        String paymentStatusString = paymentStatus != null ? paymentStatus.name() : "";
        Long orderCode = searchString != null && !searchString.isEmpty() ? Long.parseLong(searchString) : null;
        Specification<MembershipSubscription> spec = Specification.allOf(
                MembershipSubscriptionSpecification.hasStatus(statusString)
                        .and(MembershipSubscriptionSpecification.hasPaymentStatus(paymentStatusString))
                        .and(MembershipSubscriptionSpecification.hasSearchString(orderCode)));
        Page<MembershipSubscription> subscriptionPage = membershipSubscriptionRepository.findAll(spec, pageable);
        return subscriptionPage.map(membershipSubscriptionMapper::toMembershipSubscriptionDTO);
    }
}
