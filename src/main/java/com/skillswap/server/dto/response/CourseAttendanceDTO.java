package com.skillswap.server.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CourseAttendanceDTO {

    private int id;
    private UserDTO user;
    private CourseDTO course;
    private String status;
    private String createdAt;
}
