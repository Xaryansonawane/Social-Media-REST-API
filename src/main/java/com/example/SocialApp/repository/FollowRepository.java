package com.example.SocialApp.repository;

import com.example.SocialApp.models.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    List<Follow> findByFollowerId(Long followerId);
    List<Follow> findByFollowingId(Long followingId);
    long countByFollowerId(Long followerId);
    long countByFollowingId(Long followingId);
}