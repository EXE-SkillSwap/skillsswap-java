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
                user.setEmail("admin@gmail.com");
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
            // Initialize default user if not exists
            if(userRepository.findByRole(Role.USER).isEmpty()){
                User user = new User();
                user.setUsername("user");
                user.setEmail("user@gmail.com");
                user.setPassword(passwordEncoder.encode("user"));
                user.setFirstName("User");
                user.setLastName("Default");
                user.setPhoneNumber("0987654321");
                user.setRole(Role.USER);
                user.setFirstLogin(false);
                user.setBio("Default User of SkillSwap platform");
                user.setLocation("User House");
                user.setAvatarUrl(defaultAvt);
                userRepository.save(user);
                log.info("Default user initialized with username: user, password: user");
            }

            // Initialize default skills if not exists
            if(skillsRepository.count() == 0){
                List<String> defaultSkills = List.of(
                        "Java",
                        "Spring Boot",
                        "React",
                        "JavaScript",
                        "SQL",
                        "HTML",
                        "CSS",
                        "Python",
                        "Docker",
                        "Kubernetes",
                        "Machine Learning",
                        "Data Science",
                        "Cloud Computing",
                        "DevOps",
                        "Agile Methodologies",
                        "Sports",
                        "Music",
                        "Cooking",
                        "Photography",
                        "Traveling",
                        "Graphic Design",
                        "Video Editing",
                        "Public Speaking",
                        "Writing",
                        "Project Management",
                        "UI/UX Design",
                        "Information Technology",
                        "Travel",
                        "Fitness",
                        "Gaming",
                        "Guitar",
                        "Piano",
                        "Cooking",
                        "Marketing and Sales",
                        "English Language",
                        "French Language",
                        "Spanish Language",
                        "German Language",
                        "Chinese Language",
                        "Japanese Language",
                        "Korean Language"
                );
                List<Skills> skills = defaultSkills.stream().map(Skills::new).collect(Collectors.toList());
                skillsRepository.saveAll(skills);
                log.info("Default skills initialized");
            }
        };
    }
}
