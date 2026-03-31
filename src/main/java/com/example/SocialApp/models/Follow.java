package com.example.SocialApp.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "follows")
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "follower_id", nullable = false)
    private User followerUser;

    @ManyToOne
    @JoinColumn(name = "following_id", nullable = false)
    private User followingUser;

    private LocalDateTime createdAt = LocalDateTime.now();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public User getFollowerUser() { return followerUser; }
    public void setFollowerUser(User followerUser) { this.followerUser = followerUser; }
    public User getFollowingUser() { return followingUser; }
    public void setFollowingUser(User followingUser) { this.followingUser = followingUser; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
