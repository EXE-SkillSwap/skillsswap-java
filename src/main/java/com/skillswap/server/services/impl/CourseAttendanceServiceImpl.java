package com.skillswap.server.services.impl;

import com.skillswap.server.dto.response.CourseAttendanceDTO;
import com.skillswap.server.dto.response.UserDTO;
import com.skillswap.server.entities.CourseAttendance;
import com.skillswap.server.entities.Courses;
import com.skillswap.server.entities.User;
import com.skillswap.server.enums.CourseAttendanceStatus;
import com.skillswap.server.enums.CourseStatus;
import com.skillswap.server.mapper.CourseAttendanceMapper;
import com.skillswap.server.mapper.UserMapper;
import com.skillswap.server.repositories.CourseAttendanceRepository;
import com.skillswap.server.repositories.CoursesRepository;
import com.skillswap.server.services.CourseAttendanceService;
import com.skillswap.server.services.NotificationService;
import com.skillswap.server.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseAttendanceServiceImpl implements CourseAttendanceService {

    private final CourseAttendanceRepository courseAttendanceRepository;
    private final UserService userService;
    private final CoursesRepository coursesRepository;
    private final CourseAttendanceMapper courseAttendanceMapper;
    private final NotificationService notificationService;
    private final UserMapper userMapper;

    @Override
    public CourseAttendanceDTO enrollCourse(int courseId) {
        User user = userService.getAuthenticatedUser();

        Courses courses = coursesRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Không tìm thấy khóa học với ID: " + courseId));

        if(courses.getUser().getId() == user.getId()){
            throw new RuntimeException("Bạn không thể đăng ký khóa học của chính mình.");
        }
        if (courses.getStatus() != CourseStatus.APPROVED) {
            throw new RuntimeException("Khóa học chưa được phê duyệt hoặc đã bị từ chối.");
        }
        if (courseAttendanceRepository.existsByCourseIdAndUserId(courseId, user.getId())) {
            throw new RuntimeException("Bạn đã đăng ký khóa học này rồi.");
        }
        CourseAttendance newAttendance = new CourseAttendance();
        newAttendance.setUser(user);
        newAttendance.setCourse(courses);
        newAttendance.setStatus(CourseAttendanceStatus.ATTENDED);
        newAttendance = courseAttendanceRepository.save(newAttendance);

        return courseAttendanceMapper.toCourseAttendanceDTO(newAttendance);
    }

    @Override
    public Page<UserDTO> getAttendeesByCourseId(int courseId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CourseAttendance> courseAttendances = courseAttendanceRepository.findByCourseId(courseId, pageable);
        return courseAttendances.map(courseAttendance -> {
            User user = courseAttendance.getUser();
            return userMapper.userCourseDTO(user);
        });
    }
}
