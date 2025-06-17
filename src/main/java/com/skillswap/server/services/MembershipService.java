package com.skillswap.server.services;

import com.skillswap.server.dto.request.PaymentProcessRequest;
import com.skillswap.server.dto.response.MembershipDTO;
import com.skillswap.server.dto.response.MembershipSubscriptionDTO;
import com.skillswap.server.dto.response.PaymentDTO;
import com.skillswap.server.entities.MembershipSubscription;

import java.util.List;

public interface MembershipService {

    MembershipDTO createMembership(MembershipDTO membershipDTO);

    List<MembershipDTO> getAllMemberships();

    PaymentDTO createPayment(int membershipId) throws Exception;

    MembershipSubscriptionDTO processPayment(PaymentProcessRequest request);

    MembershipSubscriptionDTO getUserMembershipSubscription();
}
