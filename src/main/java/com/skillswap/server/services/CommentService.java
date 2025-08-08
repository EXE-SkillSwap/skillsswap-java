package com.skillswap.server.services;

import com.skillswap.server.dto.request.CommentRequest;
import com.skillswap.server.dto.response.CommentDTO;

import java.util.List;

public interface CommentService {

    CommentDTO addComment(int postId,CommentRequest commentRequest);

    List<CommentDTO> getComments(int postId);
}
