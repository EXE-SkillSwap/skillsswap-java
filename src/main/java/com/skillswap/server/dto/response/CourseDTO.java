package com.skillswap.server.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseDTO {

    private int id;
    private String courseName;
    private String description;
    private String link;
    private double price;
    private double rating;
    private String status;
    private String bannerUrl;
    private String achievements;
    private LocalDateTime createdAt;
    private UserDTO user;
    private boolean isEnrolled;
    private int totalEnrollments;
}
