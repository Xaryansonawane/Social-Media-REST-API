package com.example.SocialApp.services;

import com.example.SocialApp.DTOs.UserDTO;
import com.example.SocialApp.DTOs.UserRequestDTO;
import com.example.SocialApp.models.User;
import com.example.SocialApp.repository.FollowRepository;
import com.example.SocialApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${file.upload-dir}")
    private String uploadDir;

    public UserService(UserRepository userRepository, FollowRepository followRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.followRepository = followRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // ✅ Helper method to save file using NIO (fixes Tomcat temp path issue)
    private String saveFile(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

        // Get absolute path of uploads folder in project root
        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();

        // Create folder if not exists
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Save file
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return fileName;
    }

    public User insertUser(User user) {
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "EMAIL CANNOT BE EMPTY");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "PASSWORD CANNOT BE EMPTY");
        }
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "USERNAME CANNOT BE EMPTY");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User registerUser(UserRequestDTO dto) throws IOException {
        String profileFileName = null;
        String coverFileName = null;

        // ✅ Use saveFile helper instead of file.transferTo()
        if (dto.getProfileImage() != null && !dto.getProfileImage().isEmpty()) {
            profileFileName = saveFile(dto.getProfileImage());
        }

        if (dto.getCoverImage() != null && !dto.getCoverImage().isEmpty()) {
            coverFileName = saveFile(dto.getCoverImage());
        }

        User user = new User();
        user.setFullName(dto.getFullName());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setBio(dto.getBio());
        user.setProfileImage(profileFileName);
        user.setCoverImage(coverFileName);

        return userRepository.save(user);
    }

    public User uploadProfilePhoto(Long id, MultipartFile file) {
        User user = fetchUserById(id);
        try {
            // ✅ Use saveFile helper instead of file.transferTo()
            String fileName = saveFile(file);
            user.setProfileImage(fileName);
            return userRepository.save(user);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "FILE UPLOAD FAILED: " + e.getMessage());
        }
    }

    public List<UserDTO> fetchAllUser() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "NO USERS FOUND");
        }
        return users.stream().map(user -> {
            long followers = followRepository.countByFollowingUser_Id(user.getId());
            long following = followRepository.countByFollowerUser_Id(user.getId());
            return new UserDTO(user.getId(), user.getUsername(), user.getBio(), user.getProfileImage(), followers, following);
        }).toList();
    }

    public User fetchUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "USER NOT FOUND WITH ID: " + id
                ));
    }

    public User fetchUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "USER NOT FOUND WITH EMAIL: " + email
                ));
    }

    public UserDTO getUserDTO(Long userId) {
        User user = fetchUserById(userId);
        long followers = followRepository.countByFollowingUser_Id(userId);
        long following = followRepository.countByFollowerUser_Id(userId);
        return new UserDTO(userId, user.getUsername(), user.getBio(), user.getProfileImage(), followers, following);
    }
}