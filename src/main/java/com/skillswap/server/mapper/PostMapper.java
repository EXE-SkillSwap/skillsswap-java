package com.skillswap.server.mapper;

import com.skillswap.server.dto.response.PostDTO;
import com.skillswap.server.entities.Posts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostMapper {

    private final UserMapper userMapper;

    public PostDTO toPostDTO(Posts posts, boolean isLiked, int likeCount, int commentCount) {
        if (posts == null) {
            return null;
        }

        PostDTO postDTO = new PostDTO();
        postDTO.setId(posts.getId());
        postDTO.setContent(posts.getContent());
        postDTO.setCreatedAt(posts.getCreatedAt());
        postDTO.setUser(userMapper.userPostDTO(posts.getUser()));
        postDTO.setLikeCount(likeCount);
        postDTO.setCommentCount(commentCount);
        postDTO.setLiked(isLiked);

        return postDTO;
    }

    public PostDTO toPostDTO(Posts posts) {
        if (posts == null) {
            return null;
        }

        PostDTO postDTO = new PostDTO();
        postDTO.setId(posts.getId());
        postDTO.setContent(posts.getContent());
        postDTO.setCreatedAt(posts.getCreatedAt());
        postDTO.setUser(userMapper.userPostDTO(posts.getUser()));

        return postDTO;
    }
}
