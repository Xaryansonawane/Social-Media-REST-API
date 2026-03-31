package com.example.SocialApp.services;

import com.example.SocialApp.models.Comment;
import com.example.SocialApp.models.Post;
import com.example.SocialApp.models.User;
import com.example.SocialApp.repository.CommentRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    @PersistenceContext
    EntityManager entityManager;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment insertComment(Comment comment) {
        Long userId = comment.getUser().getId();
        Long postId = comment.getPost().getId();

        User user = entityManager.find(User.class, userId);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "USER NOT FOUND WITH ID: " + userId);
        }

        Post post = entityManager.find(Post.class, postId);
        if (post == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "POST NOT FOUND WITH ID: " + postId);
        }

        comment.setUser(user);
        comment.setPost(post);

        Comment savedComment = commentRepository.save(comment);

        post.setCommentCount(post.getCommentCount() + 1);
        entityManager.merge(post);

        return savedComment;
    }

    public List<Comment> fetchAllComments() {
        return commentRepository.findAll();
    }

    public List<Comment> getCommentsByPost(Long postId) {
        return commentRepository.findByPostId(postId);
    }

    public Comment fetchCommentById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "COMMENT NOT FOUND WITH ID: " + id
                ));
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
            entityManager.merge(post);
        }
    }
}
