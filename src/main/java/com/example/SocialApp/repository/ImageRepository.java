package com.example.SocialApp.repository;

import com.example.SocialApp.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> { }