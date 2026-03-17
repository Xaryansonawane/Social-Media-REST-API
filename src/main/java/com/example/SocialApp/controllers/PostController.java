package com.example.SocialApp.controllers;

import com.example.SocialApp.DTOs.PostResponseDTO;
import com.example.SocialApp.Utility.ApiResponse;
import com.example.SocialApp.services.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PostResponseDTO>>> getAllPosts() {
        List<PostResponseDTO> posts = postService.getAllPostsWithCommentsAndLikes();
        return ResponseEntity.ok(ApiResponse.success("POSTS FETCHED SUCCESSFULLY", posts));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PostResponseDTO>> getPostById(@PathVariable Long id) {
        PostResponseDTO post = postService.getPostByIdWithCommentsAndLikes(id);
        return ResponseEntity.ok(ApiResponse.success("POST FETCHED SUCCESSFULLY", post));
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<ApiResponse<List<PostResponseDTO>>> getPostsByUserId(@PathVariable Long userId) {
        List<PostResponseDTO> posts = postService.getPostsByUserId(userId);
        return ResponseEntity.ok(ApiResponse.success("POSTS FETCHED SUCCESSFULLY", posts));
    }
}