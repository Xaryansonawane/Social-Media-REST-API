package com.example.SocialApp.services;

import com.example.SocialApp.DTOs.MessageDTO;
import com.example.SocialApp.models.Message;
import com.example.SocialApp.models.User;
import com.example.SocialApp.repository.MessageRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    @PersistenceContext
    EntityManager entityManager;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message insertMessage(Message message) {
        long senderId = message.getSender().getId();
        long receiverId = message.getReceiver().getId();

        if (senderId == receiverId) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CANNOT SEND MESSAGE TO YOURSELF");
        }

        User sender = entityManager.getReference(User.class, senderId);
        User receiver = entityManager.getReference(User.class, receiverId);

        message.setSender(sender);
        message.setReceiver(receiver);

        return messageRepository.save(message);
    }

    public List<Message> fetchAllMessage() {
        return messageRepository.findAll();
    }

    public Message fetchMessageById(Long id) {
        return messageRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "MESSAGE NOT FOUND WITH ID: " + id
                ));
    }

    public Optional<Message> getMessageById(Long id) {
        return messageRepository.findById(id);
    }

    public List<Message> getMessagesBySender(Long senderId) {
        return messageRepository.findBySender_Id(senderId);
    }

    public List<Message> getMessagesByReceiver(Long receiverId) {
        return messageRepository.findByReceiver_Id(receiverId);
    }

    public List<MessageDTO> getChat(long user1, long user2) {
        List<Message> aToB = messageRepository.findBySender_IdAndReceiver_IdOrderByCreatedAtAsc(user1, user2);
        List<Message> bToA = messageRepository.findBySender_IdAndReceiver_IdOrderByCreatedAtAsc(user2, user1);

        List<Message> all = new ArrayList<>();
        all.addAll(aToB);
        all.addAll(bToA);
        all.sort(Comparator.comparing(Message::getCreatedAt));

        return all.stream()
                .map(m -> new MessageDTO(
                        m.getSender().getUsername(),
                        m.getContent()
                ))
                .toList();
    }

    public List<Message> getMessagesBetweenUsers(Long senderId, Long receiverId) {
        List<Message> aToB = messageRepository.findBySender_IdAndReceiver_Id(senderId, receiverId);
        List<Message> bToA = messageRepository.findBySender_IdAndReceiver_Id(receiverId, senderId);
        List<Message> all = new ArrayList<>();
        all.addAll(aToB);
        all.addAll(bToA);
        all.sort(Comparator.comparing(Message::getCreatedAt));
        return all;
    }

    public List<Message> getUnreadMessages(Long receiverId) {
        return messageRepository.findByReceiver_IdAndIsSeenFalse(receiverId);
    }

    public Message markMessageAsSeen(Long id) {
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "MESSAGE NOT FOUND WITH ID: " + id
                ));
        message.setSeen(true);
        return messageRepository.save(message);
    }

    public void deleteMessage(Long id) {
        if (!messageRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "MESSAGE NOT FOUND WITH ID: " + id);
        }
        messageRepository.deleteById(id);
    }
}
