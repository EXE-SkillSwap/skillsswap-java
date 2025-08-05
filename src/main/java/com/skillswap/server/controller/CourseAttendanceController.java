package com.skillswap.server.controller;

import com.skillswap.server.services.CourseAttendanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/course-attendance")
public class CourseAttendanceController {

    private final CourseAttendanceService courseAttendanceService;

    @PostMapping("/enroll/{courseId}")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Tham gia khóa học",
               description = "Người dùng phải đăng nhập để tham gia khóa học")
    public ResponseEntity<?> enrollCourse(@PathVariable int courseId){
        try {
            return ResponseEntity.ok(courseAttendanceService.enrollCourse(courseId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
