package com.skillswap.server.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PaymentDTO {
    public String code;
    public String message;
    public String paymentUrl;
    public String qrCode;
}
