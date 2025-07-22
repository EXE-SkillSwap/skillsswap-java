package com.skillswap.server.services;

public interface PaymentService {

    String confirmWebhook(String webhookUrl) throws Exception;

}
