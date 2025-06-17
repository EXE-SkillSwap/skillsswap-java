package com.skillswap.server.dto.request;

import com.skillswap.server.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PaymentProcessRequest {

    private boolean cancel;
    private PaymentStatus status;
    private long orderCode;

}
