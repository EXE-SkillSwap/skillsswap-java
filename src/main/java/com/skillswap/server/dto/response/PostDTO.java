package com.skillswap.server.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostDTO {
    private int id;
    private String content;
    private LocalDateTime createdAt;
    private UserDTO user;
    private int likeCount;
    private int commentCount;
    private boolean isLiked;
}
