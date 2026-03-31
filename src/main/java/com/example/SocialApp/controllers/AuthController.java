package com.example.SocialApp.controllers;

import com.example.SocialApp.DTOs.AuthResponseDTO;
import com.example.SocialApp.DTOs.LoginRequestDTO;
import com.example.SocialApp.DTOs.UserRequestDTO;
import com.example.SocialApp.Utility.ApiResponse;
import com.example.SocialApp.models.User;
import com.example.SocialApp.services.AuthService;
import com.example.SocialApp.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    // ✅ Changed: @RequestBody User → @ModelAttribute UserRequestDTO
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponseDTO>> register(
            @ModelAttribute UserRequestDTO userRequestDTO) throws Exception {

        AuthResponseDTO response = authService.register(userRequestDTO);
        return ResponseEntity.status(201)
                .body(ApiResponse.success("USER REGISTERED SUCCESSFULLY", response));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponseDTO>> login(
            @RequestBody LoginRequestDTO request) {
        AuthResponseDTO response = authService.login(request);
        return ResponseEntity.ok(ApiResponse.success("LOGIN SUCCESSFUL!", response));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<User>> currentUser(Authentication authentication) {
        User user = userService.fetchUserByEmail(authentication.getName());
        return ResponseEntity.ok(ApiResponse.success("CURRENT USER FETCHED SUCCESSFULLY", user));
    }
}