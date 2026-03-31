package com.example.SocialApp.repository;

import com.example.SocialApp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    User findByUsername(String username);
    boolean existsByEmail(String email);
}
