package com.skillswap.server.services.impl;

import com.skillswap.server.dto.request.CreateUserRequest;
import com.skillswap.server.dto.response.UserDTO;
import com.skillswap.server.entities.User;
import com.skillswap.server.enums.Role;
import com.skillswap.server.mapper.UserMapper;
import com.skillswap.server.repositories.UserRepository;
import com.skillswap.server.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    @Value("${app.default.avatar}")
    private String defaultAvt;

    @Override
    public UserDTO registerUser(CreateUserRequest request) {
        if(!request.getPassword().equals(request.getConfirmPassword())){
            throw new RuntimeException("Mật khẩu và xác nhận mật khẩu không khớp");
        }

        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new RuntimeException("Email đã được sử dụng");
        }
        User user = new User();
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);
        user.setUsername(request.getEmail().split("@")[0]);
        user.setAvatarUrl(defaultAvt);
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
}
