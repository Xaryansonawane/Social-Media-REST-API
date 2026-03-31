package com.example.SocialApp.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileUploadService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    public String uploadFile(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            return null;
        }

        // Validate file type - only images allowed
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "ONLY IMAGE FILES ARE ALLOWED"
            );
        }

        // Validate file size - max 5MB
        if (file.getSize() > 5 * 1024 * 1024) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "FILE SIZE CANNOT EXCEED 5MB"
            );
        }

        try {
            // Generate unique file name
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

            // Create upload directory if it does not exist
            File uploadDirectory = new File(uploadDir);
            if (!uploadDirectory.exists()) {
                uploadDirectory.mkdirs();
            }

            // Save file to upload directory
            File destination = new File(uploadDir + File.separator + fileName);
            file.transferTo(destination);

            return fileName;

        } catch (IOException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "FILE UPLOAD FAILED: " + e.getMessage()
            );
        }
    }
}