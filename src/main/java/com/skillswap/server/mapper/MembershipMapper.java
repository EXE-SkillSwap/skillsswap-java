package com.skillswap.server.mapper;

import com.skillswap.server.dto.response.MembershipDTO;
import com.skillswap.server.entities.Membership;
import org.springframework.stereotype.Component;

@Component
public class MembershipMapper {

    public MembershipDTO toMembershipDTO(Membership membership) {
        if (membership == null) {
            return null;
        }
        MembershipDTO membershipDTO = new MembershipDTO();
        membershipDTO.setId(membership.getId());
        membershipDTO.setName(membership.getName());
        membershipDTO.setDescription(membership.getDescription());
        membershipDTO.setPrice(membership.getPrice());
        membershipDTO.setDuration(membership.getDuration());
        membershipDTO.setFeatures(membership.getFeatures());

        return membershipDTO;
    }

    public MembershipDTO toMembershipDTO(Membership membership, boolean isBought) {
        if (membership == null) {
            return null;
        }
        MembershipDTO membershipDTO = new MembershipDTO();
        membershipDTO.setId(membership.getId());
        membershipDTO.setName(membership.getName());
        membershipDTO.setDescription(membership.getDescription());
        membershipDTO.setPrice(membership.getPrice());
        membershipDTO.setDuration(membership.getDuration());
        membershipDTO.setFeatures(membership.getFeatures());
        membershipDTO.setBought(isBought);

        return membershipDTO;
    }
}
