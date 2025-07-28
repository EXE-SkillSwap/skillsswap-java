package com.skillswap.server.services;

import com.skillswap.server.dto.request.MockPaymentRequest;
import vn.payos.type.Webhook;
import vn.payos.type.WebhookData;

public interface PaymentService {

    String confirmWebhook(String webhookUrl) throws Exception;

    WebhookData verifyPaymentWebhookData(Webhook req) throws Exception;

    void mockVerifyPayment(MockPaymentRequest request) throws Exception;

}
