package com.skillswap.server.repositories;

import com.skillswap.server.entities.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {

    int countByCourseId(Integer courseId);

    Page<Feedback> findByCourseId(int courseId, Pageable pageable);

    boolean existsByUserIdAndCourseId(int userId, Integer courseId);

}
