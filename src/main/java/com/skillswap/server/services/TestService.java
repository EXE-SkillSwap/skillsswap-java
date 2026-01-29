package com.skillswap.server.services;

import com.skillswap.server.dto.response.UserDTO;

public interface TestService {

    UserDTO getUserById(int id);
}
