package com.skillswap.server.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthenticationRequest {

    @NotNull(message = "Username is required")
    private String email;
    @NotNull(message = "Password is required")
    private String password;
}
