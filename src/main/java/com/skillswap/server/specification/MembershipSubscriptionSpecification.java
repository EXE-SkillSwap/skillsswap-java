package com.skillswap.server.specification;

import com.skillswap.server.entities.MembershipSubscription;
import org.springframework.data.jpa.domain.Specification;

public class MembershipSubscriptionSpecification {

    public static Specification<MembershipSubscription> hasStatus(String status){
        return (root, query, criteriaBuilder) -> {
            if (status == null || status.isEmpty()) {
                return criteriaBuilder.conjunction(); // No filter applied
            }
            return criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("status")), "%" + status.toLowerCase() + "%")
            );
        };
    }

    public static Specification<MembershipSubscription> hasPaymentStatus(String paymentStatus) {
        return (root, query, criteriaBuilder) -> {
            if (paymentStatus == null || paymentStatus.isEmpty()) {
                return criteriaBuilder.conjunction(); // No filter applied
            }
            return criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("paymentStatus")), "%" + paymentStatus.toLowerCase() + "%")
            );
        };
    }
}
