package com.example.SocialApp.controllers;

import com.example.SocialApp.Utility.ApiResponse;
import com.example.SocialApp.models.Like;
import com.example.SocialApp.services.LikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/likes")
public class LikeController {

    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Like>> insertLike(@RequestBody Like like) {
        Like result = likeService.insertLike(like);
        return ResponseEntity.ok(ApiResponse.success("LIKE ADDED SUCCESSFULLY", result));
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<ApiResponse<List<Like>>> getLikesByPost(@PathVariable Long postId) {
        List<Like> likes = likeService.getLikesByPost(postId);
        return ResponseEntity.ok(ApiResponse.success("LIKES FETCHED SUCCESSFULLY", likes));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<Like>>> getLikesByUser(@PathVariable Long userId) {
        List<Like> likes = likeService.getLikesByUser(userId);
        return ResponseEntity.ok(ApiResponse.success("LIKES FETCHED SUCCESSFULLY", likes));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteLike(@PathVariable Long id) {
        likeService.deleteLike(id);
        return ResponseEntity.ok(ApiResponse.success("LIKE REMOVED SUCCESSFULLY", null));
    }
}