package com.skillswap.server.controller;

import com.skillswap.server.dto.request.CourseCreateRequest;
import com.skillswap.server.dto.request.RejectCourseRequest;
import com.skillswap.server.dto.response.CourseDTO;
import com.skillswap.server.enums.CourseStatus;
import com.skillswap.server.services.CoursesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/courses")
public class CoursesController {

    private final CoursesService coursesService;

    @PostMapping
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<?> createCourses(@RequestBody CourseCreateRequest requests) {
        CourseDTO courseDTO = coursesService.createCourses(requests);
        return ResponseEntity.ok(courseDTO);
    }

    @GetMapping("/my-courses")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Lây danh sách khóa học của người dùng hiện tại",
            description = "Lấy danh sách khóa học đã tạo của người dùng hiện tại với phân trang")
    public ResponseEntity<?> getCoursesByCurrentUser(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(coursesService.getCoursesByCurrentUser(page, size));
    }

    @GetMapping("/attended-courses")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Lây danh sách khóa học của người dùng hiện tại đã tham gia",
            description = "Lấy danh sách khóa học đã tham gia của người dùng hiện tại với phân trang")
    public ResponseEntity<?> getAttendedCoursesByCurrentUser(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(coursesService.getAttendedCoursesByCurrentUser(page, size));
    }

    @GetMapping("/{courseId}")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Lấy thông tin chi tiết khóa học",
            description = "Lấy thông tin chi tiết của một khóa học theo ID")
    public ResponseEntity<CourseDTO> getCourseById(@PathVariable int courseId) {
        return ResponseEntity.ok(coursesService.getCourseById(courseId));
    }

    @GetMapping("/all")
    @Operation(summary = "Lấy danh sách tất cả khóa học",
            description = "Lấy danh sách tất cả khóa học với phân trang")
    public ResponseEntity<?> getAllCourses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false, name = "searchString") String searchString,
            @RequestParam(name = "status", defaultValue = "PENDING") CourseStatus status,
            @RequestParam(name = "sortBy", defaultValue = "DESC") Sort.Direction sortBy
            ) {
        return ResponseEntity.ok(coursesService.getAllCourses(page, size, searchString, status, sortBy));
    }

    @PutMapping("/approve/{courseId}")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Duyệt khóa học",
            description = "Duyệt khóa học đã được tạo và chuyển trạng thái sang APPROVED")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> approveCourse(@PathVariable int courseId) {
        return ResponseEntity.ok(coursesService.approveCourse(courseId));
    }

    @PutMapping("/reject")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Từ chối khóa học",
            description = "Từ chối học đã được tạo và chuyển trạng thái sang REJECTED")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> rejectCourse(@RequestBody RejectCourseRequest request) {
        return ResponseEntity.ok(coursesService.rejectCourse(request.getCourseId(), request.getReason()));
    }

    @GetMapping("/best-courses")
    @Operation(summary = "Lấy danh sách khóa học tốt nhất",
            description = "Lấy danh sách các khóa học tốt nhất với phân trang")
    public ResponseEntity<?> getBestCourses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok(coursesService.getBestCourses(page, size));
    }
}
