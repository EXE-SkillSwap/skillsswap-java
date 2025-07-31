package com.skillswap.server.services;

import com.skillswap.server.dto.request.PaymentProcessRequest;
import com.skillswap.server.dto.response.MembershipDTO;
import com.skillswap.server.dto.response.MembershipSubscriptionDTO;
import com.skillswap.server.entities.Membership;
import com.skillswap.server.entities.MembershipSubscription;
import vn.payos.type.CheckoutResponseData;
import vn.payos.type.PaymentLinkData;

import java.util.List;

public interface MembershipService {

    MembershipDTO createMembership(MembershipDTO membershipDTO);

    List<MembershipDTO> getAllMemberships();

    CheckoutResponseData createPayment(int membershipId) throws Exception;

    MembershipSubscriptionDTO processPayment(PaymentProcessRequest request);

    MembershipSubscriptionDTO getUserMembershipSubscription();
    List<Membership> getAllMembershipsForAdmin();

    PaymentLinkData cancelPayment(long orderCode) throws Exception;

    MembershipSubscription getValidMembershipSubscription(int userId);
}
