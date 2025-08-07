package com.skillswap.server.mapper;

import com.skillswap.server.dto.response.MembershipSubscriptionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MembershipSubscriptionMapper {

    private final MembershipMapper membershipMapper;
    private final UserMapper userMapper;

    public MembershipSubscriptionDTO toMembershipSubscriptionDTO(com.skillswap.server.entities.MembershipSubscription membershipSubscription) {
        if (membershipSubscription == null) {
            return null;
        }
        MembershipSubscriptionDTO dto = new MembershipSubscriptionDTO();
        dto.setId(membershipSubscription.getId());
        dto.setStartDate(membershipSubscription.getStartDate());
        dto.setEndDate(membershipSubscription.getEndDate());
        dto.setStatus(membershipSubscription.getStatus());
        dto.setPaymentStatus(membershipSubscription.getPaymentStatus());
        dto.setOrderCode(membershipSubscription.getOrderCode());
        dto.setUpdated(membershipSubscription.isUpdated());
        dto.setUserDTO(userMapper.userCourseDTO(membershipSubscription.getUser()));
        dto.setMembership(membershipMapper.toMembershipDTO(membershipSubscription.getMembership()));

        return dto;
    }
}
