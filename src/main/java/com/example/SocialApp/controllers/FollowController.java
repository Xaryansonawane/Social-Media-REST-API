package com.example.SocialApp.controllers;

import com.example.SocialApp.Utility.ApiResponse;
import com.example.SocialApp.models.Follow;
import com.example.SocialApp.services.FollowService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/follows")
public class FollowController {

    private final FollowService followService;

    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Follow>> insertFollow(@RequestBody Follow follow) {
        Follow result = followService.insertFollow(follow);
        return ResponseEntity.ok(ApiResponse.success("FOLLOWED SUCCESSFULLY", result));
    }

    @GetMapping("/following/{followerId}")
    public ResponseEntity<ApiResponse<List<Follow>>> getFollowing(@PathVariable Long followerId) {
        List<Follow> following = followService.getFollowing(followerId);
        return ResponseEntity.ok(ApiResponse.success("FOLLOWING FETCHED SUCCESSFULLY", following));
    }

    @GetMapping("/followers/{followingId}")
    public ResponseEntity<ApiResponse<List<Follow>>> getFollowers(@PathVariable Long followingId) {
        List<Follow> followers = followService.getFollowers(followingId);
        return ResponseEntity.ok(ApiResponse.success("FOLLOWERS FETCHED SUCCESSFULLY", followers));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteFollow(@PathVariable Long id) {
        followService.deleteFollow(id);
        return ResponseEntity.ok(ApiResponse.success("UNFOLLOWED SUCCESSFULLY", null));
    }
}