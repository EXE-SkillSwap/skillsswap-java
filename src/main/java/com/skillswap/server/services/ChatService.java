package com.skillswap.server.services;

import com.skillswap.server.dto.payload.MessagePayload;
import com.skillswap.server.dto.response.MessageDTO;
import com.skillswap.server.dto.response.UserDTO;
import com.skillswap.server.entities.Conversation;

import java.util.List;

public interface ChatService {

    Conversation createChat(int userId);

    List<UserDTO> getAllUsersConversedWith();

    List<MessageDTO> getMessageByConversationId(int recipientId);

    MessageDTO sendMessage(MessagePayload message);
}
