package com.skillswap.server.services.impl;

import com.skillswap.server.dto.request.PaymentProcessRequest;
import com.skillswap.server.dto.response.MembershipDTO;
import com.skillswap.server.dto.response.MembershipSubscriptionDTO;
import com.skillswap.server.dto.response.PaymentDTO;
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
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import vn.payos.PayOS;
import vn.payos.type.CheckoutResponseData;
import vn.payos.type.ItemData;
import vn.payos.type.PaymentData;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
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
        Membership savedMembership = membershipRepository.save(membership);

        return membershipMapper.toMembershipDTO(savedMembership);
    }

    @Override
    public List<MembershipDTO> getAllMemberships() {
        List<Membership> memberships = membershipRepository.findAllByIsDeletedFalse();
        return memberships.stream().map(membershipMapper::toMembershipDTO).collect(Collectors.toList());
    }

    @Override
    public PaymentDTO createPayment(int membershipId) throws Exception {
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
        String desc = "Thanh toán - " + user.getId() + " - " + orderCode;
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
        System.out.println(orderCode);
        return PaymentDTO.builder()
                .code(response.getStatus())
                .message(response.getDescription())
                .paymentUrl(response.getCheckoutUrl())
                .qrCode(response.getQrCode())
                .build();
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
            if(subscription.getEndDate().isAfter(now)){
                subscription.setStatus(MembershipSubscriptionStatus.EXPIRED);
                membershipSubscriptionRepository.save(subscription);
            }
        }
    }

    @Override
    public List<Membership> getAllMembershipsForAdmin() {
        return membershipRepository.findAll();
    }
}
