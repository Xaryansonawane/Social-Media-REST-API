package com.example.SocialApp.DTOs;

import java.util.List;

public class PostResponseDTO {
    private long id;
    private String caption;
    private long likeCount;
    private List<CommentResponseDTO> comments;
    private List<String> likedBy; // ✅ new field

    public PostResponseDTO(long id, String caption, long likeCount,
                           List<CommentResponseDTO> comments, List<String> likedBy) {
        this.id = id;
        this.caption = caption;
        this.likeCount = likeCount;
        this.comments = comments;
        this.likedBy = likedBy;
    }

    // Getters for JSON serialization
    public long getId() { return id; }
    public String getCaption() { return caption; }
    public long getLikeCount() { return likeCount; }
    public List<CommentResponseDTO> getComments() { return comments; }
    public List<String> getLikedBy() { return likedBy; }
}
