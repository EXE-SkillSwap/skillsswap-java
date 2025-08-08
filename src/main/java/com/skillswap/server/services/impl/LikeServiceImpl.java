package com.skillswap.server.services.impl;

import com.skillswap.server.dto.request.NotificationRequest;
import com.skillswap.server.entities.Like;
import com.skillswap.server.entities.Posts;
import com.skillswap.server.entities.User;
import com.skillswap.server.enums.NotificationType;
import com.skillswap.server.repositories.LikeRepository;
import com.skillswap.server.services.LikeService;
import com.skillswap.server.services.NotificationService;
import com.skillswap.server.services.PostsService;
import com.skillswap.server.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;
    private final UserService userService;
    private final NotificationService notificationService;
    private final PostsService postsService;


    @Override
    public void likePost(int postId) {
        User user = userService.getAuthenticatedUser();
        Posts post = postsService.getPostEntityById(postId);
        Optional<Like> existingLike = likeRepository.findByUserIdAndPostId(user.getId(), post.getId());
        existingLike.ifPresent(likeRepository::delete);
        Like like = new Like();
        like.setUser(user);
        like.setPost(post);
        likeRepository.save(like);

        if(user.getId() == post.getUser().getId()) {
            return;
        }

        NotificationRequest notification = new NotificationRequest();
        notification.setUserId(post.getUser().getId());
        notification.setUrl("/posts/" + post.getId());
        notification.setTitle("Thích bài viết");
        notification.setContent(user.getUsername() + " đã thích bài viết của bạn");
        notification.setType(NotificationType.LIKE);
        notificationService.sendNotification(notification);

    }
}
