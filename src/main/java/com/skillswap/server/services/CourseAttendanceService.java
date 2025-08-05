package com.skillswap.server.services;

import com.skillswap.server.dto.response.CourseAttendanceDTO;

public interface CourseAttendanceService {

    CourseAttendanceDTO enrollCourse(int courseId);
}
