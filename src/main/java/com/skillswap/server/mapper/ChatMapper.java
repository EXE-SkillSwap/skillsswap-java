package com.skillswap.server.mapper;

import com.skillswap.server.dto.response.MessageDTO;
import com.skillswap.server.entities.Message;
import org.springframework.stereotype.Component;

@Component
public class ChatMapper {

    public MessageDTO toMessageDTO(Message message, Integer currentUserId) {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setId(message.getId());
        messageDTO.setConversationId(message.getConversation().getId());
        messageDTO.setSenderId(message.getSender().getId());
        messageDTO.setSenderName(
                currentUserId
                        .equals(message.getSender().getId()) ?
                        "You" :
                        message.getSender().getFirstName() + " " + message.getSender().getLastName()
        );
        messageDTO.setSenderAvatar(message.getSender().getAvatarUrl());
        messageDTO.setContent(message.getContent());
        messageDTO.setCreatedAt(message.getCreatedAt());
        messageDTO.setMediaUrl(message.getMediaUrl());

        return messageDTO;
    }
}
