package com.example.SocialApp.repository;

import com.example.SocialApp.models.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    List<Follow> findByFollowerUser_Id(Long followerId);
    List<Follow> findByFollowingUser_Id(Long followingId);
    long countByFollowerUser_Id(Long id);
    long countByFollowingUser_Id(Long id);
}
