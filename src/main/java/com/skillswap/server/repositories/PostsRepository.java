package com.skillswap.server.repositories;

import com.skillswap.server.entities.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostsRepository extends JpaRepository<Posts, Integer> {
}
