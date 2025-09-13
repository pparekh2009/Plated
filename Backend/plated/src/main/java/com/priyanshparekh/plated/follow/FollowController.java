package com.priyanshparekh.plated.follow;

import com.priyanshparekh.plated.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class FollowController {

    private final FollowService followService;

    @PostMapping("/users/{follower-id}/follow/{following-id}")
    public ResponseEntity<MessageResponse> follow(@PathVariable(value = "follower-id") Long followerId, @PathVariable(value = "following-id") Long followingId) {
        followService.follow(followerId, followingId);
        return ResponseEntity.ok(MessageResponse.builder().message("Success following").build());
    }

    @DeleteMapping("users/{follower-id}/unfollow/{following-id}")
    public ResponseEntity<MessageResponse> unfollow(@PathVariable(value = "follower-id") Long followerId, @PathVariable(value = "following-id") Long followingId) {
        followService.unfollow(followerId, followingId);
        return ResponseEntity.ok(MessageResponse.builder().message("Unfollow success").build());
    }

    @GetMapping("users/{follower-id}/is-following/{following-id}")
    public ResponseEntity<Boolean> isFollowing(@PathVariable(value = "follower-id") Long followerId, @PathVariable(value = "following-id") Long followingId) {
        boolean isFollowing = followService.isFollowing(followerId, followingId);
        return ResponseEntity.ok(isFollowing);
    }

    @GetMapping("users/{user-id}/followers")
    public ResponseEntity<List<FollowRelation>> getFollowers(@PathVariable(value = "user-id") Long userId) {
        return ResponseEntity.ok(followService.getFollowers(userId));
    }

    @GetMapping("users/{user-id}/following")
    public ResponseEntity<List<FollowRelation>> getFollowing(@PathVariable(value = "user-id") Long userId) {
        return ResponseEntity.ok(followService.getFollowing(userId));
    }
}
