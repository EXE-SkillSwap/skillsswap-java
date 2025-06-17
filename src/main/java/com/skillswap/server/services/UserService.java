package com.skillswap.server.services;

import com.skillswap.server.dto.request.CreateUserRequest;
import com.skillswap.server.dto.response.UserDTO;
import com.skillswap.server.entities.User;

public interface UserService {

    UserDTO registerUser(CreateUserRequest request);

    User getAuthenticatedUser();

    void updateUserSkillTags(String skillTags);
}
