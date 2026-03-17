package com.example.SocialApp.services;

import com.example.SocialApp.models.Comment;
import com.example.SocialApp.models.Post;
import com.example.SocialApp.models.User;
import com.example.SocialApp.repository.CommentRepository;
import com.example.SocialApp.repository.PostRepository;
import com.example.SocialApp.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public CommentService(CommentRepository commentRepository, UserRepository userRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    public Comment insertComment(Comment comment) {

        User existingUser = userRepository.findById(comment.getUser().getId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "USER NOT FOUND WITH ID: " + comment.getUser().getId()
                ));

        Post existingPost = postRepository.findById(comment.getPost().getId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "POST NOT FOUND WITH ID: " + comment.getPost().getId()
                ));

        comment.setUser(existingUser);
        comment.setPost(existingPost);

        Comment savedComment = commentRepository.save(comment);

        existingPost.setCommentCount(existingPost.getCommentCount() + 1);
        postRepository.save(existingPost);

        return savedComment;
    }

    public List<Comment> getCommentsByPost(Long postId) {
        if (!postRepository.existsById(postId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "POST NOT FOUND WITH ID: " + postId
            );
        }
        return commentRepository.findByPostId(postId);
    }

    public void deleteComment(Long id) {

        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "COMMENT NOT FOUND WITH ID: " + id
                ));

        Post post = comment.getPost();
        commentRepository.deleteById(id);

        if (post.getCommentCount() > 0) {
            post.setCommentCount(post.getCommentCount() - 1);
            postRepository.save(post);
        }
    }
}