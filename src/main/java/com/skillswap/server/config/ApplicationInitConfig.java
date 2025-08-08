package com.skillswap.server.config;

import com.skillswap.server.entities.Skills;
import com.skillswap.server.entities.User;
import com.skillswap.server.enums.Role;
import com.skillswap.server.repositories.SkillsRepository;
import com.skillswap.server.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ApplicationInitConfig {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final SkillsRepository skillsRepository;
    @NonFinal
    @Value("${app.default.avatar}")
    private String defaultAvt;

    @Bean
    ApplicationRunner init() {
        return args -> {
            // Initialize default admin user if not exists
            if(userRepository.findByRole(Role.ADMIN).isEmpty()){
                User user = new User();
                user.setUsername("admin");
                user.setEmail("admin@skillsswap.io.vn");
                user.setPassword(passwordEncoder.encode("admin"));
                user.setFirstName("Admin");
                user.setLastName("SkillSwap");
                user.setPhoneNumber("0123456789");
                user.setRole(Role.ADMIN);
                user.setFirstLogin(false);
                user.setBio("Administrator of SkillSwap platform");
                user.setLocation("Admin House");
                user.setAvatarUrl(defaultAvt);
                userRepository.save(user);
                log.info("Admin initialized with username: admin, password: admin");
            }
        };
    }
}
