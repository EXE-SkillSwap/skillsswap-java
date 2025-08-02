package com.skillswap.server.controller;

import com.skillswap.server.dto.request.AuthenticationRequest;
import com.skillswap.server.dto.response.AuthenticationResponse;
import com.skillswap.server.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/login-google")
    public ResponseEntity<AuthenticationResponse> loginWithGoogle(@RequestParam("code") String code) {
        return ResponseEntity.ok(authenticationService.googleAuthentication(code));
    }
}
