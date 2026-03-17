package com.example.SocialApp.services;

import com.example.SocialApp.models.Follow;
import com.example.SocialApp.models.User;
import com.example.SocialApp.repository.FollowRepository;
import com.example.SocialApp.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    public FollowService(FollowRepository followRepository, UserRepository userRepository) {
        this.followRepository = followRepository;
        this.userRepository = userRepository;
    }

    public Follow insertFollow(Follow follow) {
        User follower = userRepository.findById(follow.getFollower().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "FOLLOWER NOT FOUND"));

        User following = userRepository.findById(follow.getFollowing().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "FOLLOWING NOT FOUND"));

        // Prevent self-follow
        if (follower.getId().equals(following.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "USER CANNOT FOLLOW THEMSELVES");
        }

        follow.setFollower(follower);
        follow.setFollowing(following);

        return followRepository.save(follow);
    }

    public List<Follow> getFollowing(Long followerId) {
        return followRepository.findByFollowerId(followerId);
    }

    public List<Follow> getFollowers(Long followingId) {
        return followRepository.findByFollowingId(followingId);
    }

    public void deleteFollow(Long id) {
        followRepository.deleteById(id);
    }
}