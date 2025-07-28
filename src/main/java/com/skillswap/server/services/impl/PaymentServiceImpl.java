package com.skillswap.server.services.impl;

import com.skillswap.server.dto.request.MockPaymentRequest;
import com.skillswap.server.entities.MembershipSubscription;
import com.skillswap.server.entities.User;
import com.skillswap.server.enums.MembershipSubscriptionStatus;
import com.skillswap.server.enums.PaymentStatus;
import com.skillswap.server.repositories.MembershipSubscriptionRepository;
import com.skillswap.server.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import vn.payos.PayOS;
import vn.payos.type.Webhook;
import vn.payos.type.WebhookData;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PayOS payOS;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final MembershipSubscriptionRepository membershipSubscriptionRepository;


    @Override
    public String confirmWebhook(String webhookUrl) {
        try {
            return payOS.confirmWebhook(webhookUrl);
        }catch (Exception e){
            throw new RuntimeException("Failed to confirm webhook: " + e.getMessage());
        }
    }

    @Override
    public WebhookData verifyPaymentWebhookData(Webhook req) throws Exception {
        WebhookData verifiedData = payOS.verifyPaymentWebhookData(req);
        if(verifiedData != null && "00".equals(verifiedData.getCode())){
            long orderCode = verifiedData.getOrderCode();
            MembershipSubscription subscription = membershipSubscriptionRepository
                    .findByOrderCode(orderCode).orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng với mã: " + orderCode));
            User user = subscription.getUser();
            subscription.setStatus(MembershipSubscriptionStatus.ACTIVE);
            subscription.setStartDate(LocalDateTime.now());
            subscription.setEndDate(LocalDateTime.now().plus(subscription.getMembership().getDuration(), ChronoUnit.DAYS));
            subscription.setPaymentStatus(PaymentStatus.COMPLETED);
            MembershipSubscription savedSubscription =  membershipSubscriptionRepository.save(subscription);

            MembershipSubscription oldSubscription = membershipSubscriptionRepository
                    .findByUserIdAndStatusAndIsUpdatedTrue(user.getId(), MembershipSubscriptionStatus.REMOVED)
                    .orElse(null);
            if (oldSubscription != null) {
                oldSubscription.setStatus(MembershipSubscriptionStatus.EXPIRED);
                membershipSubscriptionRepository.save(oldSubscription);
            }
            simpMessagingTemplate.convertAndSend("/topic/payment/" + orderCode, savedSubscription.getStatus());
        }
        return verifiedData;
    }

    @Override
    public void mockVerifyPayment(MockPaymentRequest request) throws Exception {
        MembershipSubscription subscription = membershipSubscriptionRepository
                .findByOrderCode(request.getOrderCode()).orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng với mã: " + request.getOrderCode()));
        subscription.setStatus(MembershipSubscriptionStatus.ACTIVE);
        subscription.setStartDate(LocalDateTime.now());
        subscription.setEndDate(LocalDateTime.now().plus(subscription.getMembership().getDuration(), ChronoUnit.DAYS));
        subscription.setPaymentStatus(PaymentStatus.COMPLETED);

        MembershipSubscription savedSubscription = membershipSubscriptionRepository.save(subscription);

        simpMessagingTemplate.convertAndSend("/topic/payment/" + request.getOrderCode(), savedSubscription.getStatus());
    }
}
