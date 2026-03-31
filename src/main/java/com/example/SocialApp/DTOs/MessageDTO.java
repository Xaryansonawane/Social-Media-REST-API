package com.example.SocialApp.DTOs;

public class MessageDTO {
    private String senderUsername;
    private String content;

    public MessageDTO(String senderUsername, String content) {
        this.senderUsername = senderUsername;
        this.content = content;
    }

    public String getSenderUsername() { return senderUsername; }
    public void setSenderUsername(String senderUsername) { this.senderUsername = senderUsername; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}
