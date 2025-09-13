package com.priyanshparekh.feature.profile

import com.priyanshparekh.core.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProfileRepository {

    suspend fun getUserProfile(userId: Long) = withContext(Dispatchers.IO) {
        return@withContext RetrofitInstance.api.getUserProfile(userId)
    }

    suspend fun isFollowing(followerId: Long, followingId: Long) = withContext(Dispatchers.IO) {
        return@withContext RetrofitInstance.api.isFollowing(followerId, followingId)
    }

    suspend fun follow(followerId: Long, followingId: Long) = withContext(Dispatchers.IO) {
        return@withContext RetrofitInstance.api.follow(followerId, followingId)
    }

    suspend fun unfollow(followerId: Long, followingId: Long) = withContext(Dispatchers.IO) {
        return@withContext RetrofitInstance.api.unfollow(followerId, followingId)
    }

    suspend fun getFollowers(userId: Long) = withContext(Dispatchers.IO) {
        return@withContext RetrofitInstance.api.getFollowers(userId)
    }

    suspend fun getFollowing(userId: Long) = withContext(Dispatchers.IO) {
        return@withContext RetrofitInstance.api.getFollowing(userId)
    }
}