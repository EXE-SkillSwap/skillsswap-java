package com.skillswap.server.repositories;

import com.skillswap.server.entities.Courses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoursesRepository extends JpaRepository<Courses, Integer> {

    Page<Courses> findByUserIdOrderByCreatedAtDesc(int userId, Pageable pageable);
}
