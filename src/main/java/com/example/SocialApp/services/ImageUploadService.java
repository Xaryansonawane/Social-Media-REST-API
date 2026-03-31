package com.example.SocialApp.services;

import com.example.SocialApp.model.Image;
import com.example.SocialApp.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Service
public class ImageUploadService {

    @Value("${app.upload.dir:uploads/}")
    private String uploadDir;

    @Autowired
    private ImageRepository imageRepository;

    public Image saveImage(MultipartFile file) throws IOException {

        // Validate type
        String originalName = file.getOriginalFilename();
        String extension = originalName.substring(originalName.lastIndexOf(".")).toLowerCase();
        if (!extension.matches("\\.(jpg|jpeg|png|gif|webp)")) {
            throw new IllegalArgumentException("Only image files are allowed!");
        }

        // Unique file name
        String fileName = UUID.randomUUID() + extension;

        // Create folder if not exists
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Save to disk
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // Save to DB
        Image image = new Image();
        image.setFileName(fileName);
        image.setFilePath(filePath.toString());
        image.setFileType(file.getContentType());

        return imageRepository.save(image);
    }
}