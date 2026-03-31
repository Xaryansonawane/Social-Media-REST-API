package com.example.SocialApp.controllers;

import com.example.SocialApp.DTOs.UserDTO;
import com.example.SocialApp.models.User;
import com.example.SocialApp.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ✅ Upload profile photo for existing user
    @PostMapping("/{id}/upload-profile")
    public ResponseEntity<?> uploadProfilePhoto(
            @PathVariable Long id,
            @RequestParam("image") MultipartFile file) {

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Please select an image!");
        }

        User updated = userService.uploadProfilePhoto(id, file);

        return ResponseEntity.ok().body(
                java.util.Map.of(
                        "message", "Profile image uploaded successfully",
                        "userId", updated.getId(),
                        "profileImage", updated.getProfileImage()
                )
        );
    }

    // ✅ Get all users
    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.fetchAllUser());
    }

    // ✅ Get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.fetchUserById(id));
    }
}