package com.skillswap.server.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "memberships")
public class Membership {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @Max(value = 365, message = "Duration must be less than 365 days")
    @Min(value = 1, message = "Duration must be greater than 0")
    private int duration;
    @Min(value = 1, message = "Price must be greater than 0")
    private BigDecimal price;
    private String description;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    private boolean isDeleted = false;
    @Column(columnDefinition = "TEXT")
    private String features;
    @JsonIgnore
    @OneToMany(mappedBy = "membership")
    private List<MembershipSubscription> membershipSubscriptions;
}
