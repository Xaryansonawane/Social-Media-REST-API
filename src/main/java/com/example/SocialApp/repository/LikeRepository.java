package com.example.SocialApp.repository;

import com.example.SocialApp.models.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Long> {
    long countByPost_Id(Long postId);
    List<Like> findByPost_Id(Long postId);
    List<Like> findByUser_Id(Long userId);
    boolean existsByUser_IdAndPost_Id(Long userId, Long postId);
}