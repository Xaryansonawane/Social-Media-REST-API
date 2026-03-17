package com.example.SocialApp.controllers;

import com.example.SocialApp.DTOs.MessageDTO;
import com.example.SocialApp.Utility.ApiResponse;
import com.example.SocialApp.models.Message;
import com.example.SocialApp.services.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<MessageDTO>> insertMessage(@RequestBody Message message) {
        MessageDTO result = messageService.insertMessage(message);
        return ResponseEntity.ok(ApiResponse.success("MESSAGE SENT SUCCESSFULLY", result));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Optional<MessageDTO>>> getMessageById(@PathVariable Long id) {
        Optional<MessageDTO> message = messageService.getMessageById(id);
        return ResponseEntity.ok(ApiResponse.success("MESSAGE FETCHED SUCCESSFULLY", message));
    }

    @GetMapping("/sender/{senderId}")
    public ResponseEntity<ApiResponse<List<MessageDTO>>> getMessagesBySender(@PathVariable Long senderId) {
        List<MessageDTO> messages = messageService.getMessagesBySender(senderId);
        return ResponseEntity.ok(ApiResponse.success("MESSAGES FETCHED SUCCESSFULLY", messages));
    }

    @GetMapping("/receiver/{receiverId}")
    public ResponseEntity<ApiResponse<List<MessageDTO>>> getMessagesByReceiver(@PathVariable Long receiverId) {
        List<MessageDTO> messages = messageService.getMessagesByReceiver(receiverId);
        return ResponseEntity.ok(ApiResponse.success("MESSAGES FETCHED SUCCESSFULLY", messages));
    }

    @GetMapping("/between/{senderId}/{receiverId}")
    public ResponseEntity<ApiResponse<List<MessageDTO>>> getMessagesBetweenUsers(
            @PathVariable Long senderId, @PathVariable Long receiverId) {
        List<MessageDTO> messages = messageService.getMessagesBetweenUsers(senderId, receiverId);
        return ResponseEntity.ok(ApiResponse.success("CONVERSATION FETCHED SUCCESSFULLY", messages));
    }

    @GetMapping("/unseen/{receiverId}")
    public ResponseEntity<ApiResponse<List<MessageDTO>>> getUnreadMessages(@PathVariable Long receiverId) {
        List<MessageDTO> messages = messageService.getUnreadMessages(receiverId);
        return ResponseEntity.ok(ApiResponse.success("UNREAD MESSAGES FETCHED SUCCESSFULLY", messages));
    }

    @PatchMapping("/{id}/seen")
    public ResponseEntity<ApiResponse<MessageDTO>> markMessageAsSeen(@PathVariable Long id) {
        MessageDTO message = messageService.markMessageAsSeen(id);
        return ResponseEntity.ok(ApiResponse.success("MESSAGE MARKED AS SEEN", message));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteMessage(@PathVariable Long id) {
        messageService.deleteMessage(id);
        return ResponseEntity.ok(ApiResponse.success("MESSAGE DELETED SUCCESSFULLY", null));
    }
}