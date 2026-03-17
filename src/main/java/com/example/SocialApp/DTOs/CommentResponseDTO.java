package com.example.SocialApp.DTOs;

public class CommentResponseDTO {
    private Long id;
    private String text;

    public CommentResponseDTO(Long id, String text) {
        this.id = id;
        this.text = text;
    }

    // Getters for JSON serialization
    public Long getId() { return id; }
    public String getText() { return text; }
}
