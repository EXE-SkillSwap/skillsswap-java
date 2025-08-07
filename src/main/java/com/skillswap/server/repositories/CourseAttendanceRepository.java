package com.skillswap.server.repositories;

import com.skillswap.server.entities.CourseAttendance;
import com.skillswap.server.entities.Courses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseAttendanceRepository extends JpaRepository<CourseAttendance, Integer> {

    boolean existsByCourseIdAndUserId(int courseId, int userId);

    int countByCourseId(int courseId);

    Page<CourseAttendance> findByUserId(int userId, Pageable pageable);

    Page<CourseAttendance> findByCourseId(int courseId, Pageable pageable);
}
