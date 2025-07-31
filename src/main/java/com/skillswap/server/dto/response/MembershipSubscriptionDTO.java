package com.skillswap.server.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.skillswap.server.entities.Membership;
import com.skillswap.server.entities.User;
import com.skillswap.server.enums.MembershipSubscriptionStatus;
import com.skillswap.server.enums.PaymentStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MembershipSubscriptionDTO {

    private int id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    private LocalDateTime startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    private LocalDateTime endDate;
    private MembershipSubscriptionStatus status;
    private PaymentStatus paymentStatus;
    private long orderCode;
    private boolean isUpdated = false;
    private int userId;
    private MembershipDTO membership;
}
