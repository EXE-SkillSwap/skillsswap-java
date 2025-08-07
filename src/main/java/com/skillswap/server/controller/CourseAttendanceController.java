package com.skillswap.server.controller;

import com.skillswap.server.services.CourseAttendanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/attendees/{courseId}")
    @Operation(summary = "Lấy danh sách người dùng đã tham gia khóa học",
               description = "Người dùng phải đăng nhập để xem danh sách người dùng đã tham gia khóa học")
    public ResponseEntity<?> getAttendees(@PathVariable int courseId,
                                          @RequestParam(value = "page", defaultValue = "0") int page,
                                          @RequestParam(value = "size", defaultValue = "10") int size){
        try {
            return ResponseEntity.ok(courseAttendanceService.getAttendeesByCourseId(courseId, 0, 10));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
