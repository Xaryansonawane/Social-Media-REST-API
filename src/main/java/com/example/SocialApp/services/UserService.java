package com.example.SocialApp.services;

import com.example.SocialApp.DTOs.UserDTO;
import com.example.SocialApp.models.User;
import com.example.SocialApp.repository.FollowRepository;
import com.example.SocialApp.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, FollowRepository followRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.followRepository = followRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User insertUser(User user) {

        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "EMAIL CANNOT BE EMPTY"
            );
        }

        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "PASSWORD CANNOT BE EMPTY"
            );
        }

        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "USERNAME CANNOT BE EMPTY"
            );
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public List<UserDTO> fetchAllUser() {

        List<User> users = userRepository.findAll();

        if (users.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "NO USERS FOUND"
            );
        }

        return users.stream().map(user -> {
            long followers = followRepository.countByFollowingId(user.getId());
            long following = followRepository.countByFollowerId(user.getId());
            return new UserDTO(
                    user.getId(),
                    user.getUsername(),
                    user.getBio(),
                    user.getProfileImage(),
                    followers,
                    following
            );
        }).toList();
    }

    public User fetchUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "USER NOT FOUND WITH ID: " + id
                ));
    }

    public UserDTO getUserStats(Long userId) {

        User user = fetchUserById(userId);

        long followers = followRepository.countByFollowingId(userId);
        long following = followRepository.countByFollowerId(userId);

        return new UserDTO(
                userId,
                user.getUsername(),
                user.getBio(),
                user.getProfileImage(),
                followers,
                following
        );
    }
}