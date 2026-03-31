package com.example.SocialApp.services;

import com.example.SocialApp.models.Follow;
import com.example.SocialApp.models.User;
import com.example.SocialApp.repository.FollowRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class FollowService {

    private final FollowRepository followRepository;

    @PersistenceContext
    EntityManager entityManager;

    public FollowService(FollowRepository followRepository) {
        this.followRepository = followRepository;
    }

    public Follow insertFollow(Follow follow) {
        Long followerId = follow.getFollowerUser().getId();
        Long followingId = follow.getFollowingUser().getId();

        User follower = entityManager.find(User.class, followerId);
        if (follower == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "FOLLOWER USER NOT FOUND WITH ID: " + followerId);
        }

        User following = entityManager.find(User.class, followingId);
        if (following == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "FOLLOWING USER NOT FOUND WITH ID: " + followingId);
        }

        if (followerId.equals(followingId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "USER CANNOT FOLLOW THEMSELVES");
        }

        follow.setFollowerUser(follower);
        follow.setFollowingUser(following);

        return followRepository.save(follow);
    }

    public List<Follow> fetchAllFollow() {
        return followRepository.findAll();
    }

    public Follow fetchFollowById(Long id) {
        return followRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "FOLLOW NOT FOUND WITH ID: " + id
                ));
    }

    public List<Follow> getFollowing(Long followerId) {
        return followRepository.findByFollowerUser_Id(followerId);
    }

    public List<Follow> getFollowers(Long followingId) {
        return followRepository.findByFollowingUser_Id(followingId);
    }

    public void deleteFollow(Long id) {
        if (!followRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "FOLLOW NOT FOUND WITH ID: " + id);
        }
        followRepository.deleteById(id);
    }
}
