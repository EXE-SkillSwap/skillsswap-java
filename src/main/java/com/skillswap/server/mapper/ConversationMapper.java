package com.skillswap.server.mapper;

import com.skillswap.server.dto.response.ConversationDTO;
import com.skillswap.server.entities.Conversation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConversationMapper {

    private final ParticipantMapper participantMapper;
    private final UserMapper userMapper;

    public ConversationDTO toConversationDTO(Conversation conversation) {
        if (conversation == null) {
            return null;
        }

        ConversationDTO conversationDTO = new ConversationDTO();
        conversationDTO.setId(conversation.getId());
        conversationDTO.setTitle(conversation.getTitle());
        conversationDTO.setCreatedAt(conversation.getCreatedAt());
        conversationDTO.setParticipants(
                conversation.getParticipants().stream()
                        .map(participantMapper::toParticipantDTO)
                        .toList()
        );

        return conversationDTO;
    }
}
