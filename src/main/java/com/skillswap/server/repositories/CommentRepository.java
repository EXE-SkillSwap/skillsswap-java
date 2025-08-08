package com.skillswap.server.repositories;

import com.skillswap.server.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    int countByPostId(int postId);

    List<Comment> findByPostId(int postId);
}
