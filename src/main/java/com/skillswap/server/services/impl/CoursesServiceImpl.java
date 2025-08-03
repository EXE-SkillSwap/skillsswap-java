package com.skillswap.server.services.impl;

import com.skillswap.server.dto.request.CourseCreateRequest;
import com.skillswap.server.dto.request.NotificationRequest;
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
import com.skillswap.server.services.NotificationService;
import com.skillswap.server.services.UserService;
import com.skillswap.server.specification.CoursesSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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
    private final NotificationService notificationService;

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
            courses.setBannerUrl(request.getBannerUrl());
            courses.setUser(user);
            courses = coursesRepository.save(courses);
            coursesList.add(courses);
        }
        NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.setTitle("Khóa học mới đã được tạo");
        notificationRequest.setContent("Bạn đã tạo thành công khóa học mới. Chúng tôi sẽ xem xét và phê duyệt trong thời gian sớm nhất.");
        notificationRequest.setUrl("/my-courses");
        notificationRequest.setUserId(user.getId());

        notificationService.sendNotification(notificationRequest);

        return coursesList.stream().map(courseMapper::toCourseDTO).toList();
    }

    @Override
    public Page<CourseDTO> getCoursesByCurrentUser(int page, int size) {
        User user = userService.getAuthenticatedUser();

        Page<Courses> coursesPage = coursesRepository.findByUserIdOrderByCreatedAtDesc(user.getId(), PageRequest.of(page, size));

        return coursesPage.map(courseMapper::toCourseDTO);
    }

    @Override
    public CourseDTO getCourseById(int id) {
        Courses course = coursesRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Khóa học không tồn tại"));
        return courseMapper.toCourseDTO(course);
    }


    @Override
    public Page<CourseDTO> getAllCourses(int page, int size, String searchString, CourseStatus status, Sort.Direction sortBy) {
        Specification<Courses> spec = Specification.allOf(CoursesSpecification.hasSearchString(searchString)
                .and(CoursesSpecification.hasStatus(status.name())));
        Pageable pageable = PageRequest.of(page, size, sortBy, "rating");
        Page<Courses> coursesPage = coursesRepository.findAll(spec, pageable);
        return coursesPage.map(courseMapper::toCourseDTO);
    }
}
