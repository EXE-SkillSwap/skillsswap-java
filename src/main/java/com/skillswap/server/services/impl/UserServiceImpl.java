package com.skillswap.server.services.impl;

import com.skillswap.server.dto.request.CreateUserRequest;
import com.skillswap.server.dto.response.ProfileImageDTO;
import com.skillswap.server.dto.response.UserDTO;
import com.skillswap.server.entities.ProfileImages;
import com.skillswap.server.entities.User;
import com.skillswap.server.enums.Role;
import com.skillswap.server.mapper.ProfileImageMapper;
import com.skillswap.server.mapper.UserMapper;
import com.skillswap.server.repositories.ProfileImageRepository;
import com.skillswap.server.repositories.UserRepository;
import com.skillswap.server.services.CloudinaryService;
import com.skillswap.server.services.EmailService;
import com.skillswap.server.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ProfileImageRepository profileImageRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final ProfileImageMapper profileImageMapper;
    @Value("${app.default.avatar}")
    private String defaultAvt;
    private final CloudinaryService cloudinaryService;
    private final EmailService emailService;

    @Override
    public UserDTO registerUser(CreateUserRequest request) {
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("Mật khẩu và xác nhận mật khẩu không khớp");
        }

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email đã được sử dụng");
        }
        if (LocalDate.now().getYear() - LocalDate.parse(request.getBirthDay()).getYear() < 18) {
            throw new RuntimeException("Bạn phải từ 18 tuổi trở lên để đăng ký");
        }
        User user = new User();
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);
        user.setUsername(request.getEmail().split("@")[0]);
        user.setAvatarUrl(defaultAvt);
        user.setAge(LocalDate.now().getYear() - LocalDate.parse(request.getBirthDay()).getYear());
        user.setBirthday(LocalDate.parse(request.getBirthDay()));
        user.setGender(request.getGender());
        user = userRepository.save(user);

        return userMapper.userDTO(user);
    }

    @Override
    public User getAuthenticatedUser() {
        var authenticatedUserId = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findById(Integer.parseInt(authenticatedUserId)).orElseThrow(() ->
                new RuntimeException("Người dùng không tồn tại hoặc không được xác thực"));
    }

    @Override
    public void updateUserSkillTags(String skillTags) {
        User user = getAuthenticatedUser();
        user.setSkillTags(skillTags);
        user.setFirstLogin(false);
        userRepository.save(user);
    }

    @Override
    public UserDTO updateProfile(Map<String, Object> updateRequest) {
        User user = getAuthenticatedUser();
        updateRequest.forEach((fieldName, value) -> {
            switch (fieldName) {
                case "firstName":
                    user.setFirstName((String) value);
                    break;
                case "lastName":
                    user.setLastName((String) value);
                    break;
                case "phoneNumber":
                    user.setPhoneNumber((String) value);
                    break;
                case "location":
                    user.setLocation((String) value);
                    break;
                case "bio":
                    user.setBio((String) value);
                    break;
                case "avatarUrl":
                    user.setAvatarUrl((String) value);
                    break;
                case "gender":
                    user.setGender((String) value);
                    break;
                case "birthDay":
                    user.setBirthday(LocalDate.parse((String) value));
                    user.setAge(LocalDate.now().getYear() - LocalDate.parse((String) value).getYear());
                    break;
                case "profession":
                    user.setProfession((String) value);
                    break;
                case "education":
                    user.setEducation((String) value);
                    break;

                // Add more fields as needed
            }
        });
        return userMapper.userDTO(userRepository.save(user));
    }

    @Override
    public Page<UserDTO> getAllUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        User authenticatedUser = getAuthenticatedUser();
        Page<User> users = userRepository.findAllByRoleAndIdNot(Role.USER, authenticatedUser.getId(), pageable);
        return users.map(userMapper::userDTO);
    }

    @Override
    public Page<UserDTO> getAllUsersForAdmin(int page, int size) {
        return null;
    }

    @Override
    public String uploadProfileImages(MultipartFile[] files) throws IOException {
        User authenticatedUser = getAuthenticatedUser();
        if (files != null && files.length > 0) {
            ProfileImages profileImages = new ProfileImages();
            profileImages.setUser(authenticatedUser);
            for (MultipartFile file : files) {
                Map map = cloudinaryService.upload(file);
                String publicId = (String) map.get("public_id");
                String url = (String) map.get("url");
                profileImages.setPublicId(publicId);
                profileImages.setImageUrl(url);
                authenticatedUser.getProfileImages().add(profileImages);
                userRepository.save(authenticatedUser);

            }
        }
        return "Tải lên ảnh đại diện thành công";
    }

    @Override
    public List<ProfileImageDTO> getProfileImagesByUserId(int userId) {
        List<ProfileImages> images = profileImageRepository.findByUserId(userId);
        return images.stream().map(profileImageMapper::toProfileImageDTO)
                .toList();
    }

    @Override
    public User getUserById(int userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new RuntimeException("Người dùng không tồn tại"));
    }
}
