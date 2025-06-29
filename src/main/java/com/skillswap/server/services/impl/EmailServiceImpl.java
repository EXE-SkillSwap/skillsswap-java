package com.skillswap.server.services.impl;

import com.resend.Resend;
import com.resend.services.emails.model.CreateEmailOptions;
import com.resend.services.emails.model.CreateEmailResponse;
import com.skillswap.server.services.EmailService;
import com.skillswap.server.utils.EmailUtils;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    @Value("${resend.api_key}")
    @NonFinal
    private String apiKey;
    private final EmailUtils emailUtils;

    @Override
    public void sendWelcomeEmail(String to) {
        Resend resend = new Resend(apiKey);
        CreateEmailOptions params = CreateEmailOptions.builder()
                .from("khaitranduy207@gmail.com")
                .to(to)
                .subject(emailUtils.getWelcomeEmailSubject())
                .html(emailUtils.getSkillsSwapHomePage())
                .build();

        try{
            CreateEmailResponse data = resend.emails().send(params);
            if (data == null || data.getId() == null) {
                throw new RuntimeException("Failed to send email");
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

}
