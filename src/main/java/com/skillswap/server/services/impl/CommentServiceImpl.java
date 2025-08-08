package com.skillswap.server.services.impl;

import com.skillswap.server.dto.request.CommentRequest;
import com.skillswap.server.dto.request.NotificationRequest;
import com.skillswap.server.dto.response.CommentDTO;
import com.skillswap.server.entities.Comment;
import com.skillswap.server.entities.Posts;
import com.skillswap.server.entities.User;
import com.skillswap.server.enums.NotificationType;
import com.skillswap.server.mapper.CommentMapper;
import com.skillswap.server.repositories.CommentRepository;
import com.skillswap.server.services.CommentService;
import com.skillswap.server.services.NotificationService;
import com.skillswap.server.services.PostsService;
import com.skillswap.server.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final NotificationService notificationService;
    private final UserService userService;
    private final PostsService postsService;
    private final CommentMapper commentMapper;

    @Override
    public CommentDTO addComment(int postId,CommentRequest commentRequest) {
        User user = userService.getAuthenticatedUser();
        Posts post = postsService.getPostEntityById(postId);
        Comment comment = new Comment();
        comment.setContent(commentRequest.getContent());
        comment.setUser(user);
        comment.setPost(post);
        comment = commentRepository.save(comment);
        if (user.getId() != post.getUser().getId()) {
            NotificationRequest notification = new NotificationRequest();
            notification.setUserId(post.getUser().getId());
            notification.setUrl("/posts/" + post.getId());
            notification.setTitle("Bình luận mới");
            notification.setContent(user.getUsername() + " đã bình luận bài viết của bạn");
            notification.setType(NotificationType.COMMENT);
            notificationService.sendNotification(notification);
        }
        return commentMapper.toCommentDTO(comment);
    }

    @Override
    public List<CommentDTO> getComments(int postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream().map(commentMapper::toCommentDTO).collect(Collectors.toList());
    }
}
