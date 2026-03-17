package com.example.SocialApp.DTOs;

public class UserDTO {
    private Long id;
    private String username;
    private String bio;
    private String profileImage;
    private long follower;
    private long following;

    public UserDTO(Long id, String username, String bio, String profileImage, long follower, long following) {
        this.id = id;
        this.username = username;
        this.bio = bio;
        this.profileImage = profileImage;
        this.follower = follower;
        this.following = following;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }
    public String getProfileImage() { return profileImage; }
    public void setProfileImage(String profileImage) { this.profileImage = profileImage; }
    public long getFollower() { return follower; }
    public void setFollower(long follower) { this.follower = follower; }
    public long getFollowing() { return following; }
    public void setFollowing(long following) { this.following = following; }
}