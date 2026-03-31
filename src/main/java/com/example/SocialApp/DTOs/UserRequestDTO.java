package com.example.SocialApp.DTOs;

import org.springframework.web.multipart.MultipartFile;

public class UserRequestDTO {

    private String fullName;
    private String username;
    private String email;
    private String password;
    private String bio;
    private long follower;
    private long following;
    private MultipartFile profileImage;
    private MultipartFile coverImage;

    public UserRequestDTO() {}

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }
    public long getFollower() { return follower; }
    public void setFollower(long follower) { this.follower = follower; }
    public long getFollowing() { return following; }
    public void setFollowing(long following) { this.following = following; }
    public MultipartFile getProfileImage() { return profileImage; }
    public void setProfileImage(MultipartFile profileImage) { this.profileImage = profileImage; }
    public MultipartFile getCoverImage() { return coverImage; }
    public void setCoverImage(MultipartFile coverImage) { this.coverImage = coverImage; }
}
