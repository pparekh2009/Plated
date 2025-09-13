package com.priyanshparekh.plated.follow;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
    void deleteByFollower_IdAndFollowing_Id(Long followerId, Long followingId);

    boolean existsByFollower_IdAndFollowing_Id(Long followerId, Long followingId);

    int countByFollowingId(Long userId);

    int countByFollowerId(Long userId);

    @Query(value = """
    SELECT u.id, u.display_name
    FROM users AS u
    JOIN follow AS f
    WHERE f.following_id = :userId AND u.id = f.follower_id
    """, nativeQuery = true)
    List<FollowRelationItemProjection> getFollowers(Long userId);

    @Query(value = """
    SELECT u.id, u.display_name
    FROM users AS u
    JOIN follow AS f
    WHERE f.follower_id = :userId AND u.id = f.following_id
    """, nativeQuery = true)
    List<FollowRelationItemProjection> getFollowing(Long userId);
}
