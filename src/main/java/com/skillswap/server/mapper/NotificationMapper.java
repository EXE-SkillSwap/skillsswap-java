package com.skillswap.server.mapper;

import com.skillswap.server.dto.response.NotificationDTO;
import com.skillswap.server.entities.Notification;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {

    public NotificationDTO toNotificationDTO(Notification notification) {
        if (notification == null) {
            return null;
        }

        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setId(notification.getId());
        notificationDTO.setTitle(notification.getTitle());
        notificationDTO.setContent(notification.getContent());
        notificationDTO.setCreatedAt(notification.getCreatedAt());
        notificationDTO.setUrl(notification.getUrl());
        notificationDTO.setRead(notification.isRead());

        return notificationDTO;

    }
}
