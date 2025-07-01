package com.skillswap.server.repositories;

import com.skillswap.server.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {

    List<Message> findByConversationId(Integer conversationId);
}
