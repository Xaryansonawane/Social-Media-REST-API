package com.example.SocialApp.services;

import com.example.SocialApp.models.Like;
import com.example.SocialApp.models.Post;
import com.example.SocialApp.models.User;
import com.example.SocialApp.repository.LikeRepository;
import com.example.SocialApp.repository.PostRepository;
import com.example.SocialApp.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class LikeService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public LikeService(LikeRepository likeRepository, UserRepository userRepository, PostRepository postRepository) {
        this.likeRepository = likeRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    public Like insertLike(Like like) {
        User existingUser = userRepository.findById(like.getUser().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "USER NOT FOUND"));

        Post existingPost = postRepository.findById(like.getPost().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "POST NOT FOUND"));

        // Prevent duplicate likes
        boolean alreadyLiked = likeRepository.existsByUser_IdAndPost_Id(existingUser.getId(), existingPost.getId());
        if (alreadyLiked) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "POST ALREADY LIKED BY USER");
        }

        like.setUser(existingUser);
        like.setPost(existingPost);

        Like savedLike = likeRepository.save(like);

        existingPost.setLikeCount(existingPost.getLikeCount() + 1);
        postRepository.save(existingPost);

        return savedLike;
    }

    public List<Like> getLikesByPost(Long postId) {
        return likeRepository.findByPost_Id(postId);
    }

    public List<Like> getLikesByUser(Long userId) {
        return likeRepository.findByUser_Id(userId);
    }

    public void deleteLike(Long id) {
        Like like = likeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "LIKE NOT FOUND"));
        Post post = like.getPost();
        likeRepository.deleteById(id);
        if (post.getLikeCount() > 0) {
            post.setLikeCount(post.getLikeCount() - 1);
            postRepository.save(post);
        }
    }
}