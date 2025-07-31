package com.skillswap.server.services;

import com.skillswap.server.dto.request.CourseCreateRequest;
import com.skillswap.server.dto.response.CourseDTO;

import java.util.List;

public interface CoursesService {

    List<CourseDTO> createCourses(List<CourseCreateRequest> requests);
}
