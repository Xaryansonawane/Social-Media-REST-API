package com.example.SocialApp.controllers;

import com.example.SocialApp.DTOs.MessageDTO;
import com.example.SocialApp.Utility.ApiResponse;
import com.example.SocialApp.models.Message;
import com.example.SocialApp.services.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Message>>> fetchAllMessages() {
        List<Message> messages = messageService.fetchAllMessage();
        return ResponseEntity.ok(ApiResponse.success("MESSAGES FETCHED SUCCESSFULLY", messages));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Message>> createMessage(@RequestBody Message message) {
        Message result = messageService.insertMessage(message);
        return ResponseEntity.status(201)
                .body(ApiResponse.success("MESSAGE SENT SUCCESSFULLY", result));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Message>> fetchByMsgId(@PathVariable long id) {
        Message message = messageService.fetchMessageById(id);
        return ResponseEntity.ok(ApiResponse.success("MESSAGE FETCHED SUCCESSFULLY", message));
    }

    @GetMapping("/chat/{user1}/{user2}")
    public ResponseEntity<ApiResponse<List<MessageDTO>>> getChat(
            @PathVariable long user1,
            @PathVariable long user2) {
        List<MessageDTO> messages = messageService.getChat(user1, user2);
        if (messages == null || messages.isEmpty()) {
            return ResponseEntity.status(404)
                    .body(ApiResponse.error("NO CHAT FOUND", "CHAT_NOT_FOUND"));
        }
        return ResponseEntity.ok(ApiResponse.success("CHAT FETCHED SUCCESSFULLY", messages));
    }

    @GetMapping("/sender/{senderId}")
    public ResponseEntity<ApiResponse<List<Message>>> getMessagesBySender(@PathVariable Long senderId) {
        List<Message> messages = messageService.getMessagesBySender(senderId);
        return ResponseEntity.ok(ApiResponse.success("MESSAGES FETCHED SUCCESSFULLY", messages));
    }

    @GetMapping("/receiver/{receiverId}")
    public ResponseEntity<ApiResponse<List<Message>>> getMessagesByReceiver(@PathVariable Long receiverId) {
        List<Message> messages = messageService.getMessagesByReceiver(receiverId);
        return ResponseEntity.ok(ApiResponse.success("MESSAGES FETCHED SUCCESSFULLY", messages));
    }

    @GetMapping("/between/{senderId}/{receiverId}")
    public ResponseEntity<ApiResponse<List<Message>>> getMessagesBetweenUsers(
            @PathVariable Long senderId, @PathVariable Long receiverId) {
        List<Message> messages = messageService.getMessagesBetweenUsers(senderId, receiverId);
        return ResponseEntity.ok(ApiResponse.success("CONVERSATION FETCHED SUCCESSFULLY", messages));
    }

    @GetMapping("/unseen/{receiverId}")
    public ResponseEntity<ApiResponse<List<Message>>> getUnreadMessages(@PathVariable Long receiverId) {
        List<Message> messages = messageService.getUnreadMessages(receiverId);
        return ResponseEntity.ok(ApiResponse.success("UNREAD MESSAGES FETCHED SUCCESSFULLY", messages));
    }

    @PatchMapping("/{id}/seen")
    public ResponseEntity<ApiResponse<Message>> markMessageAsSeen(@PathVariable Long id) {
        Message message = messageService.markMessageAsSeen(id);
        return ResponseEntity.ok(ApiResponse.success("MESSAGE MARKED AS SEEN", message));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteMessage(@PathVariable Long id) {
        messageService.deleteMessage(id);
        return ResponseEntity.ok(ApiResponse.success("MESSAGE DELETED SUCCESSFULLY", null));
    }
}
