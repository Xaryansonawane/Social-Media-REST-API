package com.example.SocialApp.controllers;

import com.example.SocialApp.Utility.ApiResponse;
import com.example.SocialApp.models.Comment;
import com.example.SocialApp.services.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Comment>>> fetchAllComments() {
        List<Comment> comments = commentService.fetchAllComments();
        if (comments.isEmpty()) {
            return ResponseEntity.status(404)
                    .body(ApiResponse.error("NO COMMENTS FOUND", "EMPTY_LIST"));
        }
        return ResponseEntity.ok(ApiResponse.success("COMMENTS FETCHED SUCCESSFULLY", comments));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Comment>> createComment(@RequestBody Comment comment) {
        Comment result = commentService.insertComment(comment);
        return ResponseEntity.status(201)
                .body(ApiResponse.success("COMMENT CREATED SUCCESSFULLY", result));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Comment>> fetchByCommentId(@PathVariable long id) {
        Comment comment = commentService.fetchCommentById(id);
        return ResponseEntity.ok(ApiResponse.success("COMMENT FETCHED SUCCESSFULLY", comment));
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<ApiResponse<List<Comment>>> getCommentsByPost(@PathVariable Long postId) {
        List<Comment> comments = commentService.getCommentsByPost(postId);
        return ResponseEntity.ok(ApiResponse.success("COMMENTS FETCHED SUCCESSFULLY", comments));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.ok(ApiResponse.success("COMMENT DELETED SUCCESSFULLY", null));
    }
}
