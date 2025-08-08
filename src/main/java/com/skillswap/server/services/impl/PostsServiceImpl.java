package com.skillswap.server.services.impl;

import com.skillswap.server.dto.request.CreatePostRequest;
import com.skillswap.server.dto.response.PostDTO;
import com.skillswap.server.entities.Posts;
import com.skillswap.server.entities.User;
import com.skillswap.server.mapper.PostMapper;
import com.skillswap.server.repositories.CommentRepository;
import com.skillswap.server.repositories.LikeRepository;
import com.skillswap.server.repositories.PostsRepository;
import com.skillswap.server.services.PostsService;
import com.skillswap.server.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostsServiceImpl implements PostsService {

    private final UserService userService;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;
    private final PostsRepository postsRepository;
    private final PostMapper postMapper;

    @Override
    public PostDTO createPost(CreatePostRequest request) {
        Posts post = new Posts();
        post.setContent(request.getContent());
        post.setUser(userService.getAuthenticatedUser());
        post = postsRepository.save(post);

        return postMapper.toPostDTO(post);
    }

    @Override
    public Page<PostDTO> getPosts(int page, int size) {
        User currentUser = userService.getAuthenticatedUser();
        Page<Posts> postsPage = postsRepository.findAll(PageRequest.of(page, size, Sort.Direction.DESC, "createdAt"));

        return postsPage.map(posts -> {
            boolean isLike = likeRepository.existsByUserIdAndPostId(currentUser.getId(), posts.getId());
            int commentCount = commentRepository.countByPostId(posts.getId());
            int likeCount = likeRepository.countByPostId(posts.getId());

            return postMapper.toPostDTO(posts, isLike, likeCount, commentCount);
        });
    }

    @Override
    public PostDTO getPostById(int postId) {
    User currentUser = userService.getAuthenticatedUser();
        Posts post = postsRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        boolean isLike = likeRepository.existsByUserIdAndPostId(currentUser.getId(), post.getId());
        int commentCount = commentRepository.countByPostId(post.getId());
        int likeCount = likeRepository.countByPostId(post.getId());
        return postMapper.toPostDTO(post, isLike, likeCount, commentCount);
    }

    @Override
    public Posts getPostEntityById(int id) {
    return postsRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Post not found"));
    }
}
