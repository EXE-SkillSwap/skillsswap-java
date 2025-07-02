package com.skillswap.server.repositories;

import com.skillswap.server.entities.Participant;
import com.skillswap.server.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ParticipantRepository extends JpaRepository<Participant, Integer> {

    @Query("SELECT DISTINCT u FROM User u " +
            "JOIN Participant p ON p.user.id = u.id " +
            "JOIN Participant p2 ON p2.conversation.id = p.conversation.id " +
            "WHERE p2.user.id = :currentUserId AND u.id != :currentUserId")
    List<User> findAllUsersConversedWith(@Param("currentUserId") Integer currentUserId);

    @Query("""
    SELECT DISTINCT u
    FROM Participant p1
    JOIN p1.conversation c
    JOIN Participant p2 ON p2.conversation = c
    JOIN p2.user u
    WHERE p1.user.id = :currentUserId AND u.id != :currentUserId
""")
    List<User> findUsersInConversationWith(@Param("currentUserId") int currentUserId);

    @Query("SELECT DISTINCT p FROM Participant p " +
            "JOIN User u on p.user.id = u.id J" +
            "OIN Participant p2 ON p2.conversation.id = p.conversation.id " +
            "WHERE p2.user.id = :userId AND u.id != :userId")
    List<Participant> findAllUserConversationWith(@Param("userId") int userId);
}
