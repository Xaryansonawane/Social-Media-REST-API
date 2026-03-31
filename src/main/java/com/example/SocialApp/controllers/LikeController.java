package com.example.SocialApp.controllers;

import com.example.SocialApp.Utility.ApiResponse;
import com.example.SocialApp.models.Like;
import com.example.SocialApp.services.LikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/like")
public class LikeController {

    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Like>>> fetchAllLikes() {
        List<Like> likes = likeService.fetchAllLike();
        return ResponseEntity.ok(ApiResponse.success("LIKES FETCHED SUCCESSFULLY", likes));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Like>> createLike(@RequestBody Like like) {
        Like result = likeService.insertLike(like);
        return ResponseEntity.status(201)
                .body(ApiResponse.success("LIKE ADDED SUCCESSFULLY", result));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Like>> fetchByLikeId(@PathVariable long id) {
        Like like = likeService.fetchLikeById(id);
        return ResponseEntity.ok(ApiResponse.success("LIKE FETCHED SUCCESSFULLY", like));
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
