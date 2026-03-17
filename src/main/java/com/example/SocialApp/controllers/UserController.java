package com.example.SocialApp.controllers;

import com.example.SocialApp.DTOs.LoginRequestDTO;
import com.example.SocialApp.DTOs.LoginResponseDTO;
import com.example.SocialApp.DTOs.UserDTO;
import com.example.SocialApp.Utility.ApiResponse;
import com.example.SocialApp.models.User;
import com.example.SocialApp.services.AuthService;
import com.example.SocialApp.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        UserDTO dto = userService.getUserStats(saved.getId());
        return ResponseEntity.status(201).body(ApiResponse.success("USER REGISTERED SUCCESSFULLY", dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDTO>> fetchByUserId(@PathVariable long id) {
        UserDTO user = userService.getUserStats(id);
        return ResponseEntity.ok(ApiResponse.success("USER FETCHED SUCCESSFULLY", user));
    }

    @GetMapping("/{id}/stats")
    public ResponseEntity<ApiResponse<UserDTO>> getUserStats(@PathVariable long id) {
        UserDTO stats = userService.getUserStats(id);
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

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDTO>> login(@RequestBody LoginRequestDTO request) {
        LoginResponseDTO response = authService.login(request);
        return ResponseEntity.ok(ApiResponse.success("LOGIN SUCCESSFUL!", response));
    }
}