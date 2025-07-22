package com.skillswap.server.controller;

import com.skillswap.server.dto.payload.MessagePayload;
import com.skillswap.server.dto.response.MessageDTO;
import com.skillswap.server.services.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ChatService chatService;


    @MessageMapping("/chat")
    public void processMessage(@Payload MessagePayload messagePayload){
        MessageDTO savedMsg = chatService.sendMessage(messagePayload);
        simpMessagingTemplate.convertAndSendToUser(
                "chat",
                "/queue/messages",
                savedMsg
        );
    }

    @Operation(summary = "Create a new chat")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/create/new-chat/{userId}")
    public ResponseEntity<?> createChat(@PathVariable int userId) {
        return ResponseEntity.ok(chatService.createChat(userId));
    }

    @Operation(summary = "Get all conversations for the current user, return type is List<Conversation>")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/current/conversations")
    public ResponseEntity<?> getAllConversationByCurrentUser() {
        return ResponseEntity.ok(chatService.getAllConversationsByCurrentUserId());
    }

    @Operation(summary = "Get messages by conservation ID")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/messages/conversation/{conservationId}")
    public ResponseEntity<?> getMessageByConversationId(@PathVariable int conservationId) {
        return ResponseEntity.ok(chatService.getMessageByConversationId(conservationId));
    }
}
