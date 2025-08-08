package com.skillswap.server.dto.request;

import com.skillswap.server.enums.NotificationType;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NotificationRequest {

    private String title;
    private String content;
    private int userId;
    private String url;
    private NotificationType type;
}
