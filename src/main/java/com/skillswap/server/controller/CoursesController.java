package com.skillswap.server.controller;

import com.skillswap.server.dto.request.CourseCreateRequest;
import com.skillswap.server.dto.response.CourseDTO;
import com.skillswap.server.services.CoursesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/courses")
public class CoursesController {

    private final CoursesService coursesService;

    @PostMapping
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<?> createCourses(@RequestBody List<CourseCreateRequest> requests) {
        List<CourseDTO> courseDTOs = coursesService.createCourses(requests);
        return ResponseEntity.ok(courseDTOs);
    }

    @GetMapping("/my-courses")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Lây danh sách khóa học của người dùng hiện tại",
            description = "Lấy danh sách khóa học đã tạo của người dùng hiện tại với phân trang")
    public ResponseEntity<?> getCoursesByCurrentUser(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(coursesService.getCoursesByCurrentUser(page, size));
    }

    @GetMapping("/{courseId}")
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
            @RequestParam(required = false, name = "searchString") String searchString) {
        return ResponseEntity.ok(coursesService.getAllCourses(page, size, searchString));
    }
}
