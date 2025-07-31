package com.skillswap.server.services.impl;

import com.skillswap.server.dto.request.CourseCreateRequest;
import com.skillswap.server.dto.response.CourseDTO;
import com.skillswap.server.entities.Courses;
import com.skillswap.server.entities.MembershipSubscription;
import com.skillswap.server.entities.User;
import com.skillswap.server.enums.CourseStatus;
import com.skillswap.server.exception.NotMembershipException;
import com.skillswap.server.mapper.CourseMapper;
import com.skillswap.server.repositories.CoursesRepository;
import com.skillswap.server.services.CoursesService;
import com.skillswap.server.services.MembershipService;
import com.skillswap.server.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CoursesServiceImpl implements CoursesService {

    private final CoursesRepository coursesRepository;
    private final MembershipService membershipService;
    private final CourseMapper courseMapper;
    private final UserService userService;

    @Override
    public List<CourseDTO> createCourses(List<CourseCreateRequest> requests) {
        User user = userService.getAuthenticatedUser();
        MembershipSubscription validSubscription = membershipService.getValidMembershipSubscription(user.getId());
        if(validSubscription == null){
            throw new NotMembershipException("Bạn chưa có gói membership hợp lệ để tạo khóa học");
        }
        List<Courses> coursesList = new ArrayList<>();
        for(CourseCreateRequest request : requests){
            Courses courses = new Courses();
            courses.setCourseName(request.getCourseName());
            courses.setDescription(request.getDescription());
            courses.setLink(request.getLink());
            courses.setPrice(request.getPrice());
            courses.setStatus(CourseStatus.PENDING);
            courses.setUser(user);
            courses = coursesRepository.save(courses);
            coursesList.add(courses);
        }
        return coursesList.stream().map(courseMapper::toCourseDTO).toList();
    }
}
