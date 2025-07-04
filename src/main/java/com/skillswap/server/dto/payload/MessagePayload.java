package com.skillswap.server.dto.payload;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MessagePayload {

    private Integer senderId;
    private Integer conversationId;
    private String content;
}
