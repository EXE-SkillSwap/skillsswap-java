package com.skillswap.server.repositories;

import com.skillswap.server.entities.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ConversationRepository extends JpaRepository<Conversation, Integer> {

    @Query("SELECT c FROM Conversation c " +
            "JOIN Participant p1 ON p1.conversation = c " +
            "JOIN Participant p2 ON p2.conversation = c " +
            "WHERE p1.user.id = :userId1 AND p2.user.id = :userId2 ")
    Optional<Conversation> findConversationByUserIds(@Param("userId1") Integer userId1,
                                                     @Param("userId2") Integer userId2);

    @Query("SELECT DISTINCT c FROM Conversation c " +
            "JOIN Participant p on c.id = p.conversation.id " +
            "JOIN Participant p2 on p2.conversation.id = c.id " +
            "WHERE p2.user.id = :userId")
    List<Conversation> findAllUserConversationWith(@Param("userId") int userId);
}
