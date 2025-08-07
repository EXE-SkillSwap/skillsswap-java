package com.skillswap.server.repositories;

import com.skillswap.server.entities.MembershipSubscription;
import com.skillswap.server.enums.MembershipSubscriptionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MembershipSubscriptionRepository extends JpaRepository<MembershipSubscription, Integer> {

    Optional<MembershipSubscription> findByOrderCode(long orderCode);

    Optional<MembershipSubscription> findByUserIdAndStatus(int userId, MembershipSubscriptionStatus status);

    Optional<MembershipSubscription> findByUserIdAndStatusAndIsUpdatedTrue(int userId, MembershipSubscriptionStatus status);

    List<MembershipSubscription> findByStatus(MembershipSubscriptionStatus status);

    Optional<MembershipSubscription> findByUserIdAndMembershipIdAndStatus(int userId, int membershipId, MembershipSubscriptionStatus status);

    Page<MembershipSubscription> findAll(Specification<MembershipSubscription> specification, Pageable pageable);
}
