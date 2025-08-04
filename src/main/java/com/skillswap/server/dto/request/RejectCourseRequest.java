package com.skillswap.server.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RejectCourseRequest {
    private int courseId;
    private String reason;
}
