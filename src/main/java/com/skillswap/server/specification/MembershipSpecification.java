package com.skillswap.server.specification;

import com.skillswap.server.entities.Membership;
import org.springframework.data.jpa.domain.Specification;

public class MembershipSpecification {

    public static Specification<Membership> hasSearchString(String searchString) {
        return (root, query, criteriaBuilder) -> {
            if (searchString == null || searchString.isEmpty()) {
                return criteriaBuilder.conjunction(); // No filtering if search string is empty
            }
            return criteriaBuilder.or(
                criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + searchString.toLowerCase() + "%")
            );
        };
    }

    public static Specification<Membership> hasStatus(boolean status) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isDeleted"), status);
    }
}
