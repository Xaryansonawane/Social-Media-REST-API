package com.example.SocialApp.services;

import com.example.SocialApp.DTOs.CommentResponseDTO;
import com.example.SocialApp.DTOs.PostResponseDTO;
import com.example.SocialApp.models.Post;
import com.example.SocialApp.repository.LikeRepository;
import com.example.SocialApp.repository.PostRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final LikeRepository likeRepository;

    public PostService(PostRepository postRepository, LikeRepository likeRepository) {
        this.postRepository = postRepository;
        this.likeRepository = likeRepository;
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public List<PostResponseDTO> getPostsByUserId(Long userId) {

        List<Post> posts = postRepository.findByUser_Id(userId);

        if (posts.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "NO POSTS FOUND FOR USER WITH ID: " + userId
            );
        }

        return posts.stream().map(post -> {

            long likeCount = likeRepository.countByPost_Id(post.getId());

            List<CommentResponseDTO> comments = post.getComments()
                    .stream()
                    .map(c -> new CommentResponseDTO(c.getId(), c.getText()))
                    .collect(Collectors.toList());

            List<String> likedBy = likeRepository.findByPost_Id(post.getId())
                    .stream()
                    .map(like -> like.getUser().getFullName())
                    .collect(Collectors.toList());

            return new PostResponseDTO(
                    post.getId(),
                    post.getCaption(),
                    likeCount,
                    comments,
                    likedBy
            );
        }).collect(Collectors.toList());
    }

    public List<PostResponseDTO> getAllPostsWithCommentsAndLikes() {

        List<Post> posts = postRepository.findAll();

        if (posts.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "NO POSTS FOUND"
            );
        }

        return posts.stream().map(post -> {

            long likeCount = likeRepository.countByPost_Id(post.getId());

            List<CommentResponseDTO> comments = post.getComments()
                    .stream()
                    .map(c -> new CommentResponseDTO(c.getId(), c.getText()))
                    .collect(Collectors.toList());

            List<String> likedBy = likeRepository.findByPost_Id(post.getId())
                    .stream()
                    .map(like -> like.getUser().getFullName())
                    .collect(Collectors.toList());

            return new PostResponseDTO(
                    post.getId(),
                    post.getCaption(),
                    likeCount,
                    comments,
                    likedBy
            );
        }).collect(Collectors.toList());
    }

    public PostResponseDTO getPostByIdWithCommentsAndLikes(Long id) {

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "POST NOT FOUND WITH ID: " + id
                ));

        long likeCount = likeRepository.countByPost_Id(post.getId());

        List<CommentResponseDTO> comments = post.getComments()
                .stream()
                .map(c -> new CommentResponseDTO(c.getId(), c.getText()))
                .collect(Collectors.toList());

        List<String> likedBy = likeRepository.findByPost_Id(post.getId())
                .stream()
                .map(like -> like.getUser().getFullName())
                .collect(Collectors.toList());

        return new PostResponseDTO(
                post.getId(),
                post.getCaption(),
                likeCount,
                comments,
                likedBy
        );
    }
}