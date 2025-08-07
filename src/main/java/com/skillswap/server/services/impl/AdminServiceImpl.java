package com.skillswap.server.services.impl;

import com.skillswap.server.entities.MembershipSubscription;
import com.skillswap.server.enums.PaymentStatus;
import com.skillswap.server.repositories.*;
import com.skillswap.server.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final MembershipRepository membershipRepository;
    private final UserRepository userRepository;
    private final PostsRepository postsRepository;
    private final MembershipSubscriptionRepository membershipSubscriptionRepository;
    private final CoursesRepository coursesRepository;

    @Override
    public Map<String, Object> getStatistics() {
        long totalUsers = userRepository.count();
        long totalMemberships = membershipRepository.count();
        long totalPosts = postsRepository.count();
        long totalSubscriptions = membershipSubscriptionRepository.count();
        long totalCourses = coursesRepository.count();

        Map<String, Object> response = Map.of(
            "totalUsers", totalUsers,
            "totalMemberships", totalMemberships,
            "totalPosts", totalPosts,
            "totalSubscriptions", totalSubscriptions,
            "totalCourses", totalCourses
        );
        return response;
    }

    @Override
    public Map<String, Object> getRevenue() {
        List<MembershipSubscription> paymentSuccessfulSubscriptions =
                membershipSubscriptionRepository.findByPaymentStatus(PaymentStatus.COMPLETED);
        BigDecimal totalRevenue = BigDecimal.ZERO;
        for (MembershipSubscription paymentSuccessfulSubscription : paymentSuccessfulSubscriptions) {
            totalRevenue = totalRevenue.add(paymentSuccessfulSubscription.getMembership().getPrice());
        }

        return Map.of(
            "totalRevenue", totalRevenue
        );
    }
}
