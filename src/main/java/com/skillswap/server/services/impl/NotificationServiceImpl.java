package com.skillswap.server.services.impl;

import com.skillswap.server.dto.request.NotificationRequest;
import com.skillswap.server.dto.response.NotificationDTO;
import com.skillswap.server.entities.Notification;
import com.skillswap.server.entities.User;
import com.skillswap.server.mapper.NotificationMapper;
import com.skillswap.server.repositories.NotificationRepository;
import com.skillswap.server.services.NotificationService;
import com.skillswap.server.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserService userService;
    private final NotificationMapper notificationMapper;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public void sendNotification(NotificationRequest request) {

        Notification notification = new Notification();
        notification.setTitle(request.getTitle());
        notification.setContent(request.getContent());
        notification.setUrl(request.getUrl());
        notification.setUser(userService.getUserById(request.getUserId()));

        Notification savedNotification = notificationRepository.save(notification);

        NotificationDTO dto = notificationMapper.toNotificationDTO(savedNotification);

        simpMessagingTemplate.convertAndSend("/topic/notifications/"+request.getUserId(), dto);

    }

    @Override
    public List<NotificationDTO> getNotificationsByUser() {
        User user = userService.getAuthenticatedUser();
        List<Notification> notifications = notificationRepository.findByUserIdOrderByCreatedAtDesc(user.getId());
        return notifications.stream().map(notificationMapper::toNotificationDTO).collect(Collectors.toList());
    }

    @Override
    public void makeNotificationRead(int notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thông báo với ID: " + notificationId));
        notification.setRead(true);
        notificationRepository.save(notification);
    }

    @Override
    public void makeAllNotificationsRead() {
        User user = userService.getAuthenticatedUser();
        List<Notification> notifications = notificationRepository.findByUserIdOrderByCreatedAtDesc(user.getId());
        notifications.forEach(notification -> notification.setRead(true));
        notificationRepository.saveAll(notifications);
    }
}
