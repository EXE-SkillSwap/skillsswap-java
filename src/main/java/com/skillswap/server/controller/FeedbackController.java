package com.skillswap.server.controller;

import com.skillswap.server.dto.request.FeedbackRequest;
import com.skillswap.server.services.FeedbackService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/feedbacks")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;

    @PostMapping("/send/{courseId}")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Gửi phản hồi cho khóa học",
            description = "Gửi phản hồi cho khóa học với ID cụ thể, bao gồm nội dung phản hồi và đánh giá")
    public ResponseEntity<?> sendFeedback(@PathVariable int courseId, @RequestBody FeedbackRequest request){
        return new ResponseEntity<>(feedbackService.sendFeedback(courseId, request.getContent(), request.getRating()), HttpStatus.CREATED);
    }

    @GetMapping("/{courseId}")
    @Operation(summary = "Lấy danh sách phản hồi cho khóa học",
            description = "Lấy danh sách phản hồi cho khóa học với ID cụ thể, hỗ trợ phân trang")
    public ResponseEntity<?> getFeedback(@PathVariable int courseId,
                                         @RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok(feedbackService.getFeedbacksByCourseId(courseId, 0, 10));
    }
}
