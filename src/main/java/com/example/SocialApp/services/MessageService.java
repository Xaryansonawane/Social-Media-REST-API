package com.example.SocialApp.services;

import com.example.SocialApp.DTOs.MessageDTO;
import com.example.SocialApp.models.Message;
import com.example.SocialApp.models.User;
import com.example.SocialApp.repository.MessageRepository;
import com.example.SocialApp.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public MessageService(MessageRepository messageRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    private MessageDTO toDTO(Message message) {
        return new MessageDTO(
                message.getId(),
                message.getSender().getId(),
                message.getSender().getFullName(),
                message.getSender().getUsername(),
                message.getReceiver().getId(),
                message.getReceiver().getFullName(),
                message.getReceiver().getUsername(),
                message.getContent(),
                message.isSeen(),
                message.getCreatedAt()
        );
    }

    public MessageDTO insertMessage(Message message) {

        User sender = userRepository.findById(message.getSender().getId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "SENDER NOT FOUND WITH ID: " + message.getSender().getId()
                ));

        User receiver = userRepository.findById(message.getReceiver().getId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "RECEIVER NOT FOUND WITH ID: " + message.getReceiver().getId()
                ));

        // Prevent sending message to yourself
        if (sender.getId().equals(receiver.getId())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "CANNOT SEND MESSAGE TO YOURSELF"
            );
        }

        message.setSender(sender);
        message.setReceiver(receiver);

        Message savedMessage = messageRepository.save(message);

        sender.setMessageCount(sender.getMessageCount() + 1);
        receiver.setMessageCount(receiver.getMessageCount() + 1);
        userRepository.save(sender);
        userRepository.save(receiver);

        return toDTO(savedMessage);
    }

    public Optional<MessageDTO> getMessageById(Long id) {
        return messageRepository.findById(id)
                .map(this::toDTO);
    }

    public List<MessageDTO> getMessagesBySender(Long senderId) {
        if (!userRepository.existsById(senderId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "SENDER NOT FOUND WITH ID: " + senderId
            );
        }
        return messageRepository.findBySender_Id(senderId)
                .stream().map(this::toDTO).toList();
    }

    public List<MessageDTO> getMessagesByReceiver(Long receiverId) {
        if (!userRepository.existsById(receiverId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "RECEIVER NOT FOUND WITH ID: " + receiverId
            );
        }
        return messageRepository.findByReceiver_Id(receiverId)
                .stream().map(this::toDTO).toList();
    }

    public List<MessageDTO> getMessagesBetweenUsers(Long senderId, Long receiverId) {
        if (!userRepository.existsById(senderId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "USER NOT FOUND WITH ID: " + senderId
            );
        }
        if (!userRepository.existsById(receiverId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "USER NOT FOUND WITH ID: " + receiverId
            );
        }
        List<Message> messages1 = messageRepository.findBySender_IdAndReceiver_Id(senderId, receiverId);
        List<Message> messages2 = messageRepository.findBySender_IdAndReceiver_Id(receiverId, senderId);
        List<Message> allMessages = new ArrayList<>();
        allMessages.addAll(messages1);
        allMessages.addAll(messages2);
        return allMessages.stream().map(this::toDTO).toList();
    }

    public List<MessageDTO> getUnreadMessages(Long receiverId) {
        if (!userRepository.existsById(receiverId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "RECEIVER NOT FOUND WITH ID: " + receiverId
            );
        }
        return messageRepository.findByReceiver_IdAndIsSeenFalse(receiverId)
                .stream().map(this::toDTO).toList();
    }

    public MessageDTO markMessageAsSeen(Long id) {
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "MESSAGE NOT FOUND WITH ID: " + id
                ));
        message.setSeen(true);
        return toDTO(messageRepository.save(message));
    }

    public void deleteMessage(Long id) {
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "MESSAGE NOT FOUND WITH ID: " + id
                ));
        User sender = message.getSender();
        User receiver = message.getReceiver();
        messageRepository.deleteById(id);
        if (sender.getMessageCount() > 0) {
            sender.setMessageCount(sender.getMessageCount() - 1);
            userRepository.save(sender);
        }
        if (receiver.getMessageCount() > 0) {
            receiver.setMessageCount(receiver.getMessageCount() - 1);
            userRepository.save(receiver);
        }
    }
}