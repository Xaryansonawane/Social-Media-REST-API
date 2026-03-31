package com.example.SocialApp.DTOs;

import com.example.SocialApp.models.User;

public class AuthResponseDTO {
    private String token;
    private String type;
    private User user;

    public AuthResponseDTO() {}

    public AuthResponseDTO(String token, User user) {
        this.token = token;
        this.type = "Bearer";
        this.user = user;
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
