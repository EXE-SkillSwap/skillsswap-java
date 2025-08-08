package com.skillswap.server.services;

import com.skillswap.server.dto.request.CreatePostRequest;
import com.skillswap.server.dto.response.PostDTO;
import com.skillswap.server.entities.Posts;
import org.springframework.data.domain.Page;

public interface PostsService {

    PostDTO createPost(CreatePostRequest request);

    Page<PostDTO> getPosts(int page, int size);
    PostDTO getPostById(int postId);
    Posts getPostEntityById(int id);
}
