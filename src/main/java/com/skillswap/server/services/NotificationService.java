package com.skillswap.server.services;

import com.skillswap.server.dto.request.NotificationRequest;
import com.skillswap.server.dto.response.NotificationDTO;

import java.util.List;

public interface NotificationService {

    void sendNotification(NotificationRequest request);

    List<NotificationDTO> getNotificationsByUser();

    void makeNotificationRead(int notificationId);

    void makeAllNotificationsRead(); // New method to mark all notifications as read for a user
}
