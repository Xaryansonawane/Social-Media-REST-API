package com.example.SocialApp.services;

import com.example.SocialApp.models.Like;
import com.example.SocialApp.models.Post;
import com.example.SocialApp.models.User;
import com.example.SocialApp.repository.LikeRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class LikeService {

    private final LikeRepository likeRepository;

    @PersistenceContext
    EntityManager entityManager;

    public LikeService(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    public Like insertLike(Like like) {
        Long userId = like.getUser().getId();
        Long postId = like.getPost().getId();

        User user = entityManager.find(User.class, userId);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "USER NOT FOUND WITH ID: " + userId);
        }

        Post post = entityManager.find(Post.class, postId);
        if (post == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "POST NOT FOUND WITH ID: " + postId);
        }

        boolean alreadyLiked = likeRepository.existsByUser_IdAndPost_Id(userId, postId);
        if (alreadyLiked) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "POST ALREADY LIKED BY USER");
        }

        like.setUser(user);
        like.setPost(post);

        Like savedLike = likeRepository.save(like);

        post.setLikeCount(post.getLikeCount() + 1);
        entityManager.merge(post);

        return savedLike;
    }

    public List<Like> fetchAllLike() {
        return likeRepository.findAll();
    }

    public Like fetchLikeById(Long id) {
        return likeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "LIKE NOT FOUND WITH ID: " + id
                ));
    }

    public List<Like> getLikesByPost(Long postId) {
        return likeRepository.findByPost_Id(postId);
    }

    public List<Like> getLikesByUser(Long userId) {
        return likeRepository.findByUser_Id(userId);
    }

    public void deleteLike(Long id) {
        Like like = likeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "LIKE NOT FOUND WITH ID: " + id
                ));
        Post post = like.getPost();
        likeRepository.deleteById(id);
        if (post.getLikeCount() > 0) {
            post.setLikeCount(post.getLikeCount() - 1);
            entityManager.merge(post);
        }
    }
}
