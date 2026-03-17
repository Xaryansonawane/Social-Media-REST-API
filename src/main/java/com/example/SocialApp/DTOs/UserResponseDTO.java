package com.example.SocialApp.DTOs;

public class UserResponseDTO {
    private Long userId;
    private long followers;
    private long following;

    public UserResponseDTO(Long userId, long followers, long following) {
        this.userId = userId;
        this.followers = followers;
        this.following = following;
    }

    public Long getUserId() { return userId; }
    public long getFollowers() { return followers; }
    public long getFollowing() { return following; }

    public void setUserId(Long userId) { this.userId = userId; }
    public void setFollowers(long followers) { this.followers = followers; }
    public void setFollowing(long following) { this.following = following; }
}
