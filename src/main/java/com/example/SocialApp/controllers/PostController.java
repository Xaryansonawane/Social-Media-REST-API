package com.example.SocialApp.controllers;

import com.example.SocialApp.DTOs.PostDTO;
import com.example.SocialApp.Utility.ApiResponse;
import com.example.SocialApp.models.Post;
import com.example.SocialApp.services.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PostDTO>>> getAllPosts() {
        List<PostDTO> posts = postService.getAllPosts();
        return ResponseEntity.ok(ApiResponse.success("POSTS FETCHED SUCCESSFULLY", posts));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Post>> createPost(@RequestBody Post post) {
        Post result = postService.insertPost(post);
        return ResponseEntity.status(201)
                .body(ApiResponse.success("POST CREATED SUCCESSFULLY", result));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Post>> fetchByPostId(@PathVariable long id) {
        Post post = postService.fetchPostById(id);
        return ResponseEntity.ok(ApiResponse.success("POST FETCHED SUCCESSFULLY", post));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<PostDTO>>> getPostsByUserId(@PathVariable Long userId) {
        List<PostDTO> posts = postService.getPostsByUserId(userId);
        return ResponseEntity.ok(ApiResponse.success("USER POSTS FETCHED SUCCESSFULLY", posts));
    }

    @GetMapping("/postWithComment")
    public ResponseEntity<ApiResponse<List<PostDTO>>> getPostsWithComments() {
        List<PostDTO> posts = postService.getAllPostsWithCommentsAndLikes();
        return ResponseEntity.ok(ApiResponse.success("POSTS WITH COMMENTS FETCHED SUCCESSFULLY", posts));
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<ApiResponse<List<PostDTO>>> getPostsByUser(@PathVariable Long userId) {
        List<PostDTO> posts = postService.getPostsByUserId(userId);
        return ResponseEntity.ok(ApiResponse.success("POSTS FETCHED SUCCESSFULLY", posts));
    }
}
