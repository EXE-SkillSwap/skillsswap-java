package com.skillswap.server.services;

import com.skillswap.server.dto.response.FeedbackDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface FeedbackService {

    Page<FeedbackDTO> getFeedbacksByCourseId(int courseId, int page, int size);
    FeedbackDTO sendFeedback(int courseId, String content, int rating);
}
