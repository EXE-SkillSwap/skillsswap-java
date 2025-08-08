package com.skillswap.server.repositories;

import com.skillswap.server.entities.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Integer> {

    boolean existsByUserIdAndPostId(int userId, int postId);

    int countByPostId(int postId);

    Optional<Like> findByUserIdAndPostId(int userId, int postId);
}
