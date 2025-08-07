package com.skillswap.server.controller;

import com.skillswap.server.enums.MembershipSubscriptionStatus;
import com.skillswap.server.enums.PaymentStatus;
import com.skillswap.server.services.MembershipService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/membership-subscriptions")
public class MembershipSubscriptionController {

    private final MembershipService membershipService;

    @GetMapping("/all")
    @Operation(summary = "Lấy danh sách tất cả danh sach đăng ký thành viên")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<?> getAllMembershipSubscriptions(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "searchString", required = false) String searchString,
            @RequestParam(value = "sort", defaultValue = "ASC") Sort.Direction sort,
            @RequestParam(value = "status", required = false) MembershipSubscriptionStatus status,
            @RequestParam(value = "paymentStatus", required = false) PaymentStatus paymentStatus
    ) {
        return ResponseEntity.ok(membershipService.getMembershipSubscriptions(page, size,searchString, sort, status, paymentStatus));
    }
}
