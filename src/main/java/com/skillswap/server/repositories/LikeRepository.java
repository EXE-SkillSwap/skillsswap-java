package com.skillswap.server.repositories;

import com.skillswap.server.entities.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Integer> {

    boolean existsByUserIdAndPostId(int userId, int postId);

    int countByPostId(int postId);
}
