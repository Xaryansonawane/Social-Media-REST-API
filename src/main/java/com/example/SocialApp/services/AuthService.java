package com.example.SocialApp.services;

import com.example.SocialApp.DTOs.AuthResponseDTO;
import com.example.SocialApp.DTOs.LoginRequestDTO;
import com.example.SocialApp.DTOs.UserRequestDTO;
import com.example.SocialApp.models.User;
import com.example.SocialApp.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserService userService;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService,
                       UserService userService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    public AuthResponseDTO register(UserRequestDTO dto) throws IOException {

        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "EMAIL ALREADY EXISTS");
        }

        User savedUser = userService.registerUser(dto);

        String token = jwtService.generateToken(savedUser.getEmail());
        return new AuthResponseDTO(token, savedUser);
    }

    public AuthResponseDTO login(LoginRequestDTO request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "USER NOT FOUND WITH EMAIL: " + request.getEmail()
                ));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "INVALID PASSWORD");
        }

        String token = jwtService.generateToken(user.getEmail());
        return new AuthResponseDTO(token, user);
    }
}