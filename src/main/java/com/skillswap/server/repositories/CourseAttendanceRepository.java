package com.skillswap.server.repositories;

import com.skillswap.server.entities.CourseAttendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseAttendanceRepository extends JpaRepository<CourseAttendance, Integer> {

    boolean existsByCourseIdAndUserId(int courseId, int userId);

    int countByCourseId(int courseId);
}
