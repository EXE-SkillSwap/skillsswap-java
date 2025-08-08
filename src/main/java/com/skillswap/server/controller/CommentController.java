package com.skillswap.server.controller;

import com.skillswap.server.dto.request.CommentRequest;
import com.skillswap.server.dto.response.CommentDTO;
import com.skillswap.server.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{postId}")
    public ResponseEntity<?> addComment(@PathVariable int postId,@RequestBody CommentRequest commentRequest) {
        CommentDTO commentDTO = commentService.addComment(postId, commentRequest);
        return ResponseEntity.ok(commentDTO);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<?> getComments(@PathVariable int postId) {
        return ResponseEntity.ok(commentService.getComments(postId));
    }
}
