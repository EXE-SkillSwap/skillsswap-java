package com.skillswap.server.mapper;

import com.skillswap.server.dto.response.CommentDTO;
import com.skillswap.server.entities.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentMapper {

    private final UserMapper userMapper;

    public CommentDTO toCommentDTO(Comment comment) {
        if (comment == null) {
            return null;
        }

        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setContent(comment.getContent());
        commentDTO.setCreatedAt(comment.getCreatedAt());
        commentDTO.setUser(userMapper.userPostDTO(comment.getUser()));

        return commentDTO;
    }

}
