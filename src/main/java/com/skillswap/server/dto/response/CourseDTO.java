package com.skillswap.server.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CourseDTO {

    private int id;
    private String courseName;
    private String description;
    private String link;
    private double price;
    private double rating;
    private String status;
    private LocalDateTime createdAt;
    private UserDTO user;
}
