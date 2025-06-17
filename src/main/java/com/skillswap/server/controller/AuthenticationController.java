package com.skillswap.server.controller;

import com.skillswap.server.dto.request.AuthenticationRequest;
import com.skillswap.server.dto.response.AuthenticationResponse;
import com.skillswap.server.services.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;


    @PostMapping
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

//    @Operation(
//            summary = "Test endpoint for admin role",
//            description = "This endpoint is accessible only to users with the ADMIN role."
//    )
//    @SecurityRequirement(name = "Bearer Authentication")
//    @GetMapping("/test-admin")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<String> testRoleAdmin() {
//        return ResponseEntity.ok("Test role admin");
//    }
//
//    @Operation(
//            summary = "Test endpoint for user role",
//            description = "This endpoint is accessible only to users with the USER role."
//    )
//    @SecurityRequirement(name = "Bearer Authentication")
//    @GetMapping("/test-user")
//    @PreAuthorize("hasRole('USER')")
//    public ResponseEntity<String> testRoleUser() {
//        return ResponseEntity.ok("Test role user");
//    }
}
