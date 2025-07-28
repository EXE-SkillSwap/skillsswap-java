package com.skillswap.server.controller;

import com.skillswap.server.dto.request.CancelPaymentRequest;
import com.skillswap.server.dto.request.MockPaymentRequest;
import com.skillswap.server.dto.request.WebhookUrlRequest;
import com.skillswap.server.services.MembershipService;
import com.skillswap.server.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.payos.type.Webhook;

import java.util.Map;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
@CrossOrigin("*")
public class PaymentController {

    private final PaymentService paymentService;
    private final MembershipService membershipService;

    @PostMapping("/webhook/confirm")
    public ResponseEntity<?> confirmWebhook(@RequestBody WebhookUrlRequest request){
        try {
            var response = paymentService.confirmWebhook(request.getWebhookUrl());
            return ResponseEntity.ok("Webhook confirmed successfully" + response);
        } catch (Exception e) {
            throw new RuntimeException("Failed to confirm webhook");
        }
    }

    @PostMapping("/cancel")
    public ResponseEntity<?> cancelPayment(@RequestBody CancelPaymentRequest request){
        try {
            return ResponseEntity.ok(membershipService.cancelPayment(request.getOrderCode()));
        } catch (Exception e) {
            return  ResponseEntity.badRequest()
                    .body("Failed to cancel payment: " + e.getMessage());
        }
    }

    @PostMapping("/mock-verify")
    public ResponseEntity<?> mockVerifyPayment(@RequestBody MockPaymentRequest request) {
        try {
            paymentService.mockVerifyPayment(request);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Failed to verify payment: " + e.getMessage());
        }
    }

    @PostMapping("/webhook/verify")
    public ResponseEntity<Map<String, Boolean>> verifyWebhook(@RequestBody Webhook request){
        try {
            var response = paymentService.verifyPaymentWebhookData(request);
            return ResponseEntity.ok(Map.of("success", true));
        } catch (Exception e) {
            return ResponseEntity.ok(Map.of("success", false));
        }
    }
}
