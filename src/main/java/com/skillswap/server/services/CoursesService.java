package com.skillswap.server.services;

import com.skillswap.server.dto.request.CourseCreateRequest;
import com.skillswap.server.dto.response.CourseDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CoursesService {

    List<CourseDTO> createCourses(List<CourseCreateRequest> requests);

    Page<CourseDTO> getCoursesByCurrentUser(int page, int size);
}
