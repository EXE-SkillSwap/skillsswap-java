package com.skillswap.server.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class FeedbackDTO {
    private int rating;
    private String content;
    private UserDTO user;

}
