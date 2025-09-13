package com.priyanshparekh.plated.follow;

import com.priyanshparekh.plated.user.User;
import com.priyanshparekh.plated.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    public void follow(Long followerId, Long followingId) {
        User followerUser = userRepository.findById(followerId).orElseThrow();
        User followingUser = userRepository.findById(followingId).orElseThrow();

        Follow follow = new Follow();
        follow.setFollowing(followingUser);
        follow.setFollower(followerUser);

        followRepository.save(follow);
    }

    @Transactional
    public void unfollow(Long followerId, Long followingId) {
        followRepository.deleteByFollower_IdAndFollowing_Id(followerId, followingId);
    }

    public boolean isFollowing(Long followerId, Long followingId) {
        return followRepository.existsByFollower_IdAndFollowing_Id(followerId, followingId);
    }

    public List<FollowRelation> getFollowers(Long userId) {
        List<FollowRelationItemProjection> followersProjectionList = followRepository.getFollowers(userId);
        log.info("followService: getFollowers: followersProjectionList: size: {}", followersProjectionList.size());
        List<FollowRelation> followers = followersProjectionList
                .stream()
                .map(projection -> {
                    System.out.println("getFollowers map: project name: " + projection.getDisplayName());
                    return FollowRelation.builder()
                            .id(projection.getId())
                            .name(projection.getDisplayName())
                            .build();
                })
                .toList();
        followers.forEach(followRelation -> System.out.println("Follower name: " + followRelation.getName()));
        return followers;
    }

    public List<FollowRelation> getFollowing(Long userId) {
        List<FollowRelationItemProjection> followingProjectionList = followRepository.getFollowing(userId);
        log.info("followService: getFollowing: followingProjectionList: size: {}", followingProjectionList.size());
        List<FollowRelation> following = followingProjectionList
                .stream()
                .map(projection -> {
                    System.out.println("getFollowing map: project name: " + projection.getDisplayName());
                    return FollowRelation.builder()
                            .id(projection.getId())
                            .name(projection.getDisplayName())
                            .build();
                })
                .toList();
        following.forEach(followRelation -> System.out.println("Following name: " + followRelation.getName()));
        return following;
    }
}
