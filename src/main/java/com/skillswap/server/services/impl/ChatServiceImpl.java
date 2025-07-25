package com.skillswap.server.services.impl;

import com.skillswap.server.dto.payload.MessagePayload;
import com.skillswap.server.dto.response.ConversationDTO;
import com.skillswap.server.dto.response.MessageDTO;
import com.skillswap.server.entities.Conversation;
import com.skillswap.server.entities.Message;
import com.skillswap.server.entities.Participant;
import com.skillswap.server.entities.User;
import com.skillswap.server.mapper.ChatMapper;
import com.skillswap.server.mapper.ConversationMapper;
import com.skillswap.server.repositories.ConversationRepository;
import com.skillswap.server.repositories.MessageRepository;
import com.skillswap.server.repositories.ParticipantRepository;
import com.skillswap.server.repositories.UserRepository;
import com.skillswap.server.services.ChatService;
import com.skillswap.server.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatServiceImpl implements ChatService {

    private final UserService userService;
    private final UserRepository userRepository;
    private final ConversationRepository conversationRepository;
    private final ParticipantRepository participantRepository;
    private final MessageRepository messageRepository;
    private final ChatMapper chatMapper;
    private final ConversationMapper conversationMapper;

    @Override
    public Conversation createChat(int userId) {
        User requestUser = userService.getAuthenticatedUser();
        User requestedUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng với ID: " + userId));

        Conversation isExistedConversation = conversationRepository
                .findConversationByUserIds(requestedUser.getId(), requestUser.getId())
                .orElseGet(() -> {
                    Conversation conversation = new Conversation();
                    conversation.setTitle(requestUser.getUsername() + " - " + requestedUser.getUsername());
                    conversation.setCreatedAt(Date.valueOf(LocalDate.now()));
                    conversationRepository.save(conversation);
                    Participant participant = new Participant();
                    participant.setConversation(conversation);
                    participant.setUser(requestUser);
                    participantRepository.save(participant);
                    participant = new Participant();
                    participant.setConversation(conversation);
                    participant.setUser(requestedUser);
                    participantRepository.save(participant);

                    return conversation;
                });

        log.info("Cuộc trò chuyện đã được tạo hoặc đã tồn tại giữa {} và {}",
                requestUser.getUsername(), requestedUser.getUsername());
        return isExistedConversation;
    }

    @Override
    public List<MessageDTO> getMessageByConversationId(int conservationId ) {
        User requestUser = userService.getAuthenticatedUser();
        Conversation conversation = conversationRepository.findById(conservationId)
                .orElseThrow(() -> new RuntimeException("Conversation not found"));

        List<Message> messageList = messageRepository.findByConversationId(conversation.getId());

        return messageList.stream()
                .map(message ->
                        chatMapper.toMessageDTO(message, requestUser.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public MessageDTO sendMessage(MessagePayload message) {
        User sender = userRepository.findById(message.getSenderId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Conversation conversation = conversationRepository
                .findById(message.getConversationId())
        .orElseThrow(() -> new RuntimeException("Conversation not found"));

        Message newMessage = new Message();
        newMessage.setContent(message.getContent());
        newMessage.setSender(sender);
        newMessage.setConversation(conversation);
        newMessage.setCreatedAt(LocalDateTime.now());

        messageRepository.save(newMessage);
        return chatMapper.toMessageDTO(newMessage, sender.getId());
    }

    @Override
    public List<ConversationDTO> getAllConversationsByCurrentUserId() {
        User user = userService.getAuthenticatedUser();
        List<Conversation> conversations = conversationRepository.findAllUserConversationWith(user.getId());

        // Remove the current user from the participants list in each conversation
        for(Conversation conversation : conversations){
            if(!conversation.getParticipants().isEmpty()){
                conversation.setParticipants(
                        conversation.getParticipants()
                                .stream()
                                .filter(participant -> participant.getUser().getId() != user.getId()).collect(Collectors.toList())
                );
            }
        }

        return conversations.stream().map(conversationMapper::toConversationDTO).collect(Collectors.toList());
    }
}
