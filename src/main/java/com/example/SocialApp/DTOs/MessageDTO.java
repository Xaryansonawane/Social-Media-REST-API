package com.example.SocialApp.DTOs;

import java.time.LocalDateTime;

public class MessageDTO
{
    private Long id;
    private Long senderId;
    private String senderName;
    private String senderUsername;
    private Long receiverId;
    private String receiverName;
    private String receiverUsername;
    private String content;
    private boolean seen;
    private LocalDateTime createdAt;

    public MessageDTO(Long id, Long senderId, String senderName, String senderUsername,
                      Long receiverId, String receiverName, String receiverUsername,
                      String content, boolean seen, LocalDateTime createdAt) {
        this.id = id;
        this.senderId = senderId;
        this.senderName = senderName;
        this.senderUsername = senderUsername;
        this.receiverId = receiverId;
        this.receiverName = receiverName;
        this.receiverUsername = receiverUsername;
        this.content = content;
        this.seen = seen;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverUsername() {
        return receiverUsername;
    }

    public void setReceiverUsername(String receiverUsername) {
        this.receiverUsername = receiverUsername;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
