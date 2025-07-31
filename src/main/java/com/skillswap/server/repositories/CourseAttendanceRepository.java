package com.skillswap.server.repositories;

import com.skillswap.server.entities.CourseAttendance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseAttendanceRepository extends JpaRepository<CourseAttendance, Integer> {
}
