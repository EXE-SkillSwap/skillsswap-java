package com.skillswap.server.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NotificationDTO {

    private int id;
    private String title;
    private String content;
    private String url;
    private LocalDateTime createdAt;
    private boolean isRead;
}
