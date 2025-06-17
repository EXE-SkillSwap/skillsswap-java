package com.skillswap.server.services;

import com.skillswap.server.dto.request.AuthenticationRequest;
import com.skillswap.server.dto.response.AuthenticationResponse;

public interface AuthenticationService {

    AuthenticationResponse authenticate(AuthenticationRequest request);

}
