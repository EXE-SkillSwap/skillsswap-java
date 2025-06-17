package com.skillswap.server.entities;

import com.skillswap.server.enums.MembershipSubscriptionStatus;
import com.skillswap.server.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "membership_subscriptions")
public class MembershipSubscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    @Enumerated(EnumType.STRING)
    private MembershipSubscriptionStatus status;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    private long orderCode;
    private boolean isUpdated = false;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @ManyToOne
    private User user;
    @ManyToOne
    private Membership membership;

}
