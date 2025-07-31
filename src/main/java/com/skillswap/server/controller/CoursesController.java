package com.skillswap.server.controller;

import com.skillswap.server.dto.request.CourseCreateRequest;
import com.skillswap.server.dto.response.CourseDTO;
import com.skillswap.server.services.CoursesService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/courses")
public class CoursesController {

    private final CoursesService coursesService;

    @PostMapping
    @SecurityRequirement(name = "Beaerer Authentication")
    public ResponseEntity<?> createCourses(@RequestBody List<CourseCreateRequest> requests) {
        List<CourseDTO> courseDTOs = coursesService.createCourses(requests);
        return ResponseEntity.ok(courseDTOs);
    }
}
