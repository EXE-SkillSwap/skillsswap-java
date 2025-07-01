package com.skillswap.server.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@Builder
@Getter
@Setter
@NoArgsConstructor
public class MessageDTO {
    private int id;
    private int conversationId;
    private int senderId;
    private String senderName;
    private String senderAvatar;
    private String content;
    private LocalDateTime createdAt;
    private String mediaUrl;
}
