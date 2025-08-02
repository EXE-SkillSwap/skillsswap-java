package com.skillswap.server.services;

import com.skillswap.server.dto.request.CreateUserRequest;
import com.skillswap.server.dto.response.ProfileImageDTO;
import com.skillswap.server.dto.response.UserDTO;
import com.skillswap.server.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface UserService {

    UserDTO registerUser(CreateUserRequest request);

    User getAuthenticatedUser();

    void updateUserSkillTags(String skillTags);

    UserDTO updateProfile(Map<String, Object> updateRequest);

    Page<UserDTO> getAllUsers(int page, int size);

    Page<UserDTO> getAllUsersForAdmin(int page, int size);

    String uploadProfileImages(List<ProfileImageDTO> dtos) throws IOException;

    List<ProfileImageDTO> getProfileImagesByUserId(int userId);

    User getUserById(int userId);
}
