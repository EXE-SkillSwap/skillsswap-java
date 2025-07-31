package com.skillswap.server.dto.request;

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
}
