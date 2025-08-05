package com.skillswap.server.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CourseCreateRequest {

    private String courseName;
    private String description;
    private String link;
    private double price;
    private String bannerUrl;
    private String achievements;
}
