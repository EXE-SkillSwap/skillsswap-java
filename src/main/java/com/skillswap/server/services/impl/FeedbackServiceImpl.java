package com.skillswap.server.services.impl;

import com.skillswap.server.dto.response.FeedbackDTO;
import com.skillswap.server.entities.Courses;
import com.skillswap.server.entities.Feedback;
import com.skillswap.server.entities.User;
import com.skillswap.server.mapper.FeedbackMapper;
import com.skillswap.server.repositories.CoursesRepository;
import com.skillswap.server.repositories.FeedbackRepository;
import com.skillswap.server.services.FeedbackService;
import com.skillswap.server.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

    private final UserService userService;
    private final FeedbackRepository feedbackRepository;
    private final CoursesRepository coursesRepository;
    private final FeedbackMapper feedbackMapper;

    @Override
    public Page<FeedbackDTO> getFeedbacksByCourseId(int courseId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Feedback> feedbackPage = feedbackRepository.findByCourseId(courseId, pageable);
        return feedbackPage.map(feedbackMapper::toFeedbackDTO);
    }

    @Override
    public FeedbackDTO sendFeedback(int courseId, String content, int rating) {
        User currentUser = userService.getAuthenticatedUser();
        Courses courses = coursesRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Không tìm thấy khóa học với ID: " + courseId));

        if(feedbackRepository.existsByUserIdAndCourseId(currentUser.getId(), courses.getId())){
            throw new RuntimeException("Bạn đã gửi phản hồi cho khóa học này rồi.");
        }

        Feedback feedback = new Feedback();
        feedback.setContent(content);
        feedback.setRating(rating);
        feedback.setUser(currentUser);
        feedback.setCourse(courses);
        feedback = feedbackRepository.save(feedback);
        int totalCurrentCourseFeedback = feedbackRepository.countByCourseId(courseId);
        double newRating = (courses.getRating() * (totalCurrentCourseFeedback - 1) + rating) / totalCurrentCourseFeedback;
        if (totalCurrentCourseFeedback == 1) {
            newRating = rating; // Nếu là phản hồi đầu tiên, đánh giá mới sẽ là đánh giá hiện tại
        } else if (totalCurrentCourseFeedback == 0) {
            newRating = 0; // Nếu không có phản hồi nào, đánh giá sẽ là 0
        } else if (totalCurrentCourseFeedback < 0) {
            throw new RuntimeException("Số lượng phản hồi không hợp lệ: " + totalCurrentCourseFeedback);
        }
        courses.setRating(newRating);
        coursesRepository.save(courses);

        return feedbackMapper.toFeedbackDTO(feedback);
    }
}
