package com.skillswap.server.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.skillswap.server.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    private int id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String role;
    private String avatarUrl;
    private String bio;
    private String location;
    private String phoneNumber;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String skillTags;
    private String profession;
    private String education;
    private int age;
    private String gender;
    private String birthday;
    private List<ProfileImageDTO> profileImages;
}
