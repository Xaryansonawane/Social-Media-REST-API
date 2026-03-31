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

import java.io.File;
import java.io.IOException;
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
        MultipartFile profileFile = dto.getProfileImage();
        MultipartFile coverFile = dto.getCoverImage();

        String profileFileName = null;
        String coverFileName = null;

        if (profileFile != null && !profileFile.isEmpty()) {
            profileFileName = UUID.randomUUID() + "_" + profileFile.getOriginalFilename();
            File dest = new File(uploadDir + File.separator + profileFileName);
            dest.getParentFile().mkdirs();
            profileFile.transferTo(dest);
        }

        if (coverFile != null && !coverFile.isEmpty()) {
            coverFileName = UUID.randomUUID() + "_" + coverFile.getOriginalFilename();
            File dest1 = new File(uploadDir + File.separator + coverFileName);
            dest1.getParentFile().mkdirs();
            coverFile.transferTo(dest1);
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
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            File dest = new File(uploadDir + File.separator + fileName);
            dest.getParentFile().mkdirs();
            file.transferTo(dest);
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
