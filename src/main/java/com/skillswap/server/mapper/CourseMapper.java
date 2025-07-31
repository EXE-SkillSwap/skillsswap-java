package com.skillswap.server.mapper;

import com.skillswap.server.dto.response.CourseDTO;
import com.skillswap.server.entities.Courses;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CourseMapper {

    private final UserMapper userMapper;

    public CourseDTO toCourseDTO(Courses courses){
        if (courses == null) {
            return null;
        }

        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setId(courses.getId());
        courseDTO.setCourseName(courses.getCourseName());
        courseDTO.setDescription(courses.getDescription());
        courseDTO.setLink(courses.getLink());
        courseDTO.setPrice(courses.getPrice());
        courseDTO.setRating(courses.getRating());
        courseDTO.setStatus(courses.getStatus().name());
        courseDTO.setCreatedAt(courses.getCreatedAt());
        courseDTO.setUser(userMapper.userCourseDTO(courses.getUser()));
        return courseDTO;
    }
}
