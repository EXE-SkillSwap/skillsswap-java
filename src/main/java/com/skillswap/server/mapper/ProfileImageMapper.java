package com.skillswap.server.mapper;

import com.skillswap.server.dto.response.ProfileImageDTO;
import com.skillswap.server.entities.ProfileImages;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProfileImageMapper {

    public ProfileImageDTO toProfileImageDTO(ProfileImages profileImages) {
        if (profileImages == null) {
            return null;
        }
        ProfileImageDTO profileImageDTO = new ProfileImageDTO();
        profileImageDTO.setId(profileImages.getId());
        profileImageDTO.setImageUrl(profileImages.getImageUrl());
        profileImageDTO.setPublicId(profileImages.getPublicId());
        return profileImageDTO;
    }

    public List<ProfileImageDTO> toProfileImageDTOs(List<ProfileImages> profileImages) {
        if (profileImages == null || profileImages.isEmpty()) {
            return List.of();
        }
        return profileImages.stream()
                .map(this::toProfileImageDTO)
                .toList();
    }

}
