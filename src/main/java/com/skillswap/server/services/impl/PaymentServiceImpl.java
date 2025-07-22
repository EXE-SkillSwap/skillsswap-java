package com.skillswap.server.services.impl;

import com.skillswap.server.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.payos.PayOS;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PayOS payOS;


    @Override
    public String confirmWebhook(String webhookUrl) throws Exception {
        try {
            return payOS.confirmWebhook(webhookUrl);
        }catch (Exception e){
            // Log the exception or handle it as needed
            throw new RuntimeException("Failed to confirm webhook: " + e.getMessage());
        }
    }
}
