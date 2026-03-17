package com.example.SocialApp.repository;

import com.example.SocialApp.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findBySender_Id(Long senderId);
    List<Message> findByReceiver_Id(Long receiverId);
    List<Message> findBySender_IdAndReceiver_Id(Long senderId, Long receiverId);
    List<Message> findByReceiver_IdAndIsSeenFalse(Long receiverId);
}