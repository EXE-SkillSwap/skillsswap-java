package com.skillswap.server.services.impl;

import com.skillswap.server.dto.response.UserDTO;
import com.skillswap.server.mapper.UserMapper;
import com.skillswap.server.services.TestService;
import com.skillswap.server.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final UserService userService;
    private final UserMapper userMapper;

    @Cacheable(value = "userCache", key = "#id")
    @Override
    public UserDTO getUserById(int id) {
        System.out.println("Call DB");
        return userMapper.userDTO(userService.getUserById(id));
    }
}
