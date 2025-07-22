package com.skillswap.server.controller;

import com.skillswap.server.dto.request.WebhookUrlRequest;
import com.skillswap.server.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
@CrossOrigin("*")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/webhook/confirm")
    public ResponseEntity<?> confirmWebhook(@RequestBody WebhookUrlRequest request){
        try {
            var response = paymentService.confirmWebhook(request.getWebhookUrl());
            return ResponseEntity.ok("Webhook confirmed successfully" + response);
        } catch (Exception e) {
            throw new RuntimeException("Failed to confirm webhook");
        }
    }

}
