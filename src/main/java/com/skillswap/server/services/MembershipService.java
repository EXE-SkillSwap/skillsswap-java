package com.skillswap.server.services;

import com.skillswap.server.dto.request.PaymentProcessRequest;
import com.skillswap.server.dto.response.MembershipDTO;
import com.skillswap.server.dto.response.MembershipSubscriptionDTO;
import com.skillswap.server.entities.Membership;
import com.skillswap.server.entities.MembershipSubscription;
import com.skillswap.server.enums.MembershipSubscriptionStatus;
import com.skillswap.server.enums.PaymentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import vn.payos.type.CheckoutResponseData;
import vn.payos.type.PaymentLinkData;

import java.util.List;

public interface MembershipService {

    MembershipDTO createMembership(MembershipDTO membershipDTO);

    List<MembershipDTO> getAllMemberships();

    CheckoutResponseData createPayment(int membershipId) throws Exception;

    MembershipSubscriptionDTO processPayment(PaymentProcessRequest request);

    MembershipSubscriptionDTO getUserMembershipSubscription();
    Page<Membership> getAllMembershipsForAdmin(
            int page,
            int size,
            Sort.Direction sort,
            String searchString,
            boolean status
    );

    PaymentLinkData cancelPayment(long orderCode) throws Exception;

    MembershipSubscription getValidMembershipSubscription(int userId);

    void deleteMembership(int membershipId);

    MembershipDTO updateMembership(int membershipId, MembershipDTO membershipDTO);

    Page<MembershipSubscriptionDTO> getMembershipSubscriptions(
            int page,
            int size,
            String searchString,
            Sort.Direction sort,
            MembershipSubscriptionStatus status,
            PaymentStatus paymentStatus
    );

}
