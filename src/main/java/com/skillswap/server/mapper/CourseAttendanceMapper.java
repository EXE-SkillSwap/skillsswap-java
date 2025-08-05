package com.skillswap.server.mapper;

import com.skillswap.server.dto.response.CourseAttendanceDTO;
import com.skillswap.server.entities.CourseAttendance;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CourseAttendanceMapper {

    private final UserMapper userMapper;
    private final CourseMapper courseMapper;

    public CourseAttendanceDTO toCourseAttendanceDTO(CourseAttendance courseAttendance) {
        if (courseAttendance == null) {
            return null;
        }

        CourseAttendanceDTO courseAttendanceDTO = new CourseAttendanceDTO();
        courseAttendanceDTO.setId(courseAttendance.getId());
        courseAttendanceDTO.setUser(userMapper.userCourseDTO(courseAttendance.getUser()));
        courseAttendanceDTO.setCourse(courseMapper.toCourseDTO(courseAttendance.getCourse()));
        courseAttendanceDTO.setStatus(courseAttendance.getStatus().name());
        courseAttendanceDTO.setCreatedAt(courseAttendance.getCreatedAt().toString());

        return courseAttendanceDTO;
    }
}
