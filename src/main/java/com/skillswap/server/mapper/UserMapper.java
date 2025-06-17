package com.skillswap.server.mapper;

import com.skillswap.server.dto.response.UserDTO;
import com.skillswap.server.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO userDTO(User user) {
        if (user == null) {
            return null;
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setRole(user.getRole().name());
        userDTO.setAvatarUrl(user.getAvatarUrl());
        userDTO.setBio(user.getBio());
        userDTO.setLocation(user.getLocation());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setCreatedAt(user.getCreatedAt());
        userDTO.setUpdatedAt(user.getUpdatedAt());

        return userDTO;
    }
}
