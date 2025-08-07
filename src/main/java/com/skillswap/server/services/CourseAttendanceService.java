package com.skillswap.server.services;

import com.skillswap.server.dto.response.CourseAttendanceDTO;
import com.skillswap.server.dto.response.UserDTO;
import com.skillswap.server.entities.User;
import org.springframework.data.domain.Page;

public interface CourseAttendanceService {

    CourseAttendanceDTO enrollCourse(int courseId);

    Page<UserDTO> getAttendeesByCourseId(int courseId, int page, int size);
}
