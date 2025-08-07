package com.skillswap.server.controller;

import com.skillswap.server.dto.request.CreatePostRequest;
import com.skillswap.server.services.PostsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostsController {

    private final PostsService postsService;

    @PostMapping
    @Operation(summary = "Tạo bài viết mới")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<?> createPost(@RequestBody CreatePostRequest request) {
        return new ResponseEntity<>(postsService.createPost(request), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Danh sách bài viết")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<?> getPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return new ResponseEntity<>(postsService.getPosts(page, size), HttpStatus.OK);
    }
}
