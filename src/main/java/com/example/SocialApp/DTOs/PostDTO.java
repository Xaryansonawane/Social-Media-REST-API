package com.example.SocialApp.DTOs;

import java.util.List;

public class PostDTO {
    private long id;
    private String caption;
    private long likeCount;
    private List<CommentDTO> comments;
    private List<String> likedBy;

    public PostDTO(long id, String caption, long likeCount, List<CommentDTO> comments, List<String> likedBy) {
        this.id = id;
        this.caption = caption;
        this.likeCount = likeCount;
        this.comments = comments;
        this.likedBy = likedBy;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getCaption() { return caption; }
    public void setCaption(String caption) { this.caption = caption; }
    public long getLikeCount() { return likeCount; }
    public void setLikeCount(long likeCount) { this.likeCount = likeCount; }
    public List<CommentDTO> getComments() { return comments; }
    public void setComments(List<CommentDTO> comments) { this.comments = comments; }
    public List<String> getLikedBy() { return likedBy; }
    public void setLikedBy(List<String> likedBy) { this.likedBy = likedBy; }
}
