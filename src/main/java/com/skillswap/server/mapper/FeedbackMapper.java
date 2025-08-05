package com.skillswap.server.mapper;

import com.skillswap.server.dto.response.FeedbackDTO;
import com.skillswap.server.entities.Feedback;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FeedbackMapper {

    private final UserMapper userMapper;

    public FeedbackDTO toFeedbackDTO(Feedback feedback) {
        if (feedback == null) {
            return null;
        }

        FeedbackDTO feedbackDTO = new FeedbackDTO();
        feedbackDTO.setRating(feedback.getRating());
        feedbackDTO.setContent(feedback.getContent());
        feedbackDTO.setUser(userMapper.userCourseDTO(feedback.getUser()));

        return feedbackDTO;
    }
}
