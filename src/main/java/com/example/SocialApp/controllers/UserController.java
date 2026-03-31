package com.example.SocialApp.controllers;

import com.example.SocialApp.DTOs.UserDTO;
import com.example.SocialApp.DTOs.UserRequestDTO;
import com.example.SocialApp.Utility.ApiResponse;
import com.example.SocialApp.models.User;
import com.example.SocialApp.services.AuthService;
import com.example.SocialApp.services.UserService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    public UserController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserDTO>>> fetchAllUser() {
        List<UserDTO> users = userService.fetchAllUser();
        return ResponseEntity.ok(ApiResponse.success("USERS FETCHED SUCCESSFULLY", users));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UserDTO>> createUser(@RequestBody User user) {
        User saved = userService.insertUser(user);
        UserDTO dto = userService.getUserDTO(saved.getId());
        return ResponseEntity.status(201)
                .body(ApiResponse.success("USER CREATED SUCCESSFULLY", dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDTO>> fetchByUserId(@PathVariable long id) {
        UserDTO user = userService.getUserDTO(id);
        return ResponseEntity.ok(ApiResponse.success("USER FETCHED SUCCESSFULLY", user));
    }

    @GetMapping("/{id}/stats")
    public ResponseEntity<ApiResponse<UserDTO>> getUserStats(@PathVariable long id) {
        UserDTO stats = userService.getUserDTO(id);
        return ResponseEntity.ok(ApiResponse.success("USER STATS FETCHED SUCCESSFULLY", stats));
    }

    @GetMapping("/reponse/{id}")
    public ResponseEntity<ApiResponse<User>> getUser(@PathVariable long id) {
        User user = userService.fetchUserById(id);
        if (user == null) {
            return ResponseEntity.status(404)
                    .body(ApiResponse.error("USER NOT FOUND", "USER_NOT_FOUND"));
        }
        return ResponseEntity.ok(ApiResponse.success("USER FETCHED SUCCESSFULLY", user));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerWithImage(
            @RequestParam String fullName,
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam(required = false) String bio,
            @RequestParam(required = false) MultipartFile profileImage,
            @RequestParam(required = false) MultipartFile coverImage
    ) {
        try {
            UserRequestDTO dto = new UserRequestDTO();
            dto.setFullName(fullName);
            dto.setUsername(username);
            dto.setEmail(email);
            dto.setPassword(password);
            dto.setBio(bio);
            dto.setProfileImage(profileImage);
            dto.setCoverImage(coverImage);

            User saved = userService.registerUser(dto);
            UserDTO userDTO = userService.getUserDTO(saved.getId());
            return ResponseEntity.status(201)
                    .body(ApiResponse.success("USER REGISTERED SUCCESSFULLY WITH IMAGE", userDTO));
        } catch (IOException e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("FILE UPLOAD FAILED", e.getMessage()));
        }
    }

    @PostMapping(value = "/{id}/profile-photo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<User>> uploadProfilePhoto(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file
    ) {
        User updatedUser = userService.uploadProfilePhoto(id, file);
        return ResponseEntity.ok(ApiResponse.success("PROFILE PHOTO UPLOADED SUCCESSFULLY", updatedUser));
    }

    @GetMapping("/image/{fileName}")
    public ResponseEntity<Resource> getImage(@PathVariable String fileName) throws IOException {
        Path path = Paths.get("uploads").resolve(fileName).normalize();
        Resource resource = new UrlResource(path.toUri());

        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }

        String contentType = Files.probeContentType(path);
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + "\"")
                .body(resource);
    }
}
