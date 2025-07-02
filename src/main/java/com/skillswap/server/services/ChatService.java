package com.skillswap.server.services;

import com.skillswap.server.dto.payload.MessagePayload;
import com.skillswap.server.dto.response.ConversationDTO;
import com.skillswap.server.dto.response.MessageDTO;
import com.skillswap.server.dto.response.UserDTO;
import com.skillswap.server.entities.Conversation;
import com.skillswap.server.entities.Participant;

import java.util.List;

public interface ChatService {

    Conversation createChat(int userId);

    List<MessageDTO> getMessageByConversationId(int conservationId);

    MessageDTO sendMessage(MessagePayload message);

    List<ConversationDTO> getAllConversationsByCurrentUserId();
}
