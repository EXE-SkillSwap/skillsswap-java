package com.skillswap.server.controller;

import com.skillswap.server.dto.request.PaymentProcessRequest;
import com.skillswap.server.dto.response.MembershipDTO;
import com.skillswap.server.dto.response.PaymentDTO;
import com.skillswap.server.entities.Membership;
import com.skillswap.server.services.MembershipService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.payos.type.CheckoutResponseData;

import java.util.List;

@RestController
@RequestMapping("/api/memberships")
@RequiredArgsConstructor
public class MembershipController {

    private final MembershipService membershipService;

    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Create a new membership",
               description = "This endpoint allows the creation of a new membership. Only ADMIN can access." +
                       "The request body must contain valid membership details.")
    @PostMapping
    public ResponseEntity<MembershipDTO> createMembership(@Valid @RequestBody MembershipDTO dto){
        return new ResponseEntity<>(membershipService.createMembership(dto), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Get all memberships for admin",
               description = "This endpoint retrieves all memberships. Only ADMIN can access this endpoint.")
    @GetMapping("/admin")
    public ResponseEntity<List<Membership>> getAllMembershipsForAdmin() {
        return new ResponseEntity<>(membershipService.getAllMembershipsForAdmin(), HttpStatus.OK);
    }

    @Operation(summary = "Get all memberships",
               description = "This endpoint retrieves all memberships. No authentication is required.")
    @GetMapping
    public ResponseEntity<List<MembershipDTO>> getMemberships(){
        return new ResponseEntity<>(membershipService.getAllMemberships(), HttpStatus.OK);
    }

    @PostMapping("/payment/{membershipId}")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Create a payment for a membership",
               description = "This endpoint allows users to create a payment for a specific membership." +
                       "The request body must contain the membership ID.")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CheckoutResponseData> createMembershipPayment(@PathVariable int membershipId) throws Exception {
        return new ResponseEntity<>(membershipService.createPayment(membershipId), HttpStatus.CREATED);
    }

    @PostMapping("/payment/process")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Process payment for a membership",
            description = "This endpoint allows users to process a payment for a specific membership." +
                    "The request body must contain the OrderCode.")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> processMembershipPayment(@RequestBody PaymentProcessRequest request) throws Exception {
        return ResponseEntity.ok(membershipService.processPayment(request));
    }

    @GetMapping("/my")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Get user membership",
            description = "This endpoint retrieves the current user's membership subscription." +
                    "Only authenticated users with the USER role can access this endpoint.")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getUserMembership() throws Exception {
        return ResponseEntity.ok(membershipService.getUserMembershipSubscription());
    }


}
