package com.skillswap.server.repositories;

import com.skillswap.server.entities.Skills;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillsRepository extends JpaRepository<Skills, Integer> {
}
