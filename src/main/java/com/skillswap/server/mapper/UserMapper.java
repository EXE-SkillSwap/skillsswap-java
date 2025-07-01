package com.skillswap.server.mapper;

import com.skillswap.server.dto.response.UserDTO;
import com.skillswap.server.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final ProfileImageMapper profileImageMapper;

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
        userDTO.setSkillTags(user.getSkillTags());
        userDTO.setProfession(user.getProfession());
        userDTO.setEducation(user.getEducation());
        userDTO.setGender(user.getGender());
        userDTO.setAge(user.getAge());
        userDTO.setBirthday(user.getBirthday() != null ? user.getBirthday().toString() : null);
        userDTO.setProfileImages(profileImageMapper.toProfileImageDTOs(user.getProfileImages()));

        return userDTO;
    }

    public UserDTO chatUserDTO(User user) {
        if (user == null) {
            return null;
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setAvatarUrl(user.getAvatarUrl());
        return userDTO;
    }
}
