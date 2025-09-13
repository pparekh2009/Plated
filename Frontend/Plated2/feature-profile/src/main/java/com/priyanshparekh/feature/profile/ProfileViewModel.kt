package com.priyanshparekh.feature.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.priyanshparekh.core.model.MessageResponse
import com.priyanshparekh.core.model.profile.FollowRelation
import com.priyanshparekh.core.model.profile.UserProfileDto
import com.priyanshparekh.core.network.Status
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {

    private val profileRepository = ProfileRepository()

    private val _userProfileStatus = MutableLiveData<Status<UserProfileDto>>()
    val userProfileStatus: LiveData<Status<UserProfileDto>> = _userProfileStatus

    fun getUserProfile(userId: Long) {
        viewModelScope.launch {
            val response = profileRepository.getUserProfile(userId)
            val errorString = response.errorBody()?.string()

            if (response.isSuccessful) {
                val body = response.body()

                if (body != null) {
                    _userProfileStatus.value = Status.SUCCESS(body)
                } else {
                    Log.d("TAG", "getUserProfile: profileViewModel: inner if:")
                    _userProfileStatus.value = Status.ERROR("Error: $errorString")
                }
            } else {
                Log.d("TAG", "getUserProfile: profileViewModel: outer if: code: ${response.code()}")
                _userProfileStatus.value = Status.ERROR("Error: $errorString")
            }
        }
    }


    private val _isFollowingStatus = MutableLiveData<Status<Boolean>>()
    val isFollowingStatus: LiveData<Status<Boolean>> = _isFollowingStatus

    fun isFollowing(followerId: Long, followingId: Long) {
        viewModelScope.launch {
            val response = profileRepository.isFollowing(followerId, followingId)
            val errorString = response.errorBody()?.string()

            if (response.isSuccessful) {
                val body = response.body()

                if (body != null) {
                    _isFollowingStatus.value = Status.SUCCESS(body)
                } else {
                    Log.d("TAG", "isFollowing: profileViewModel: inner if:")
                    _isFollowingStatus.value = Status.ERROR("Error: $errorString")
                }
            } else {
                Log.d("TAG", "isFollowing: profileViewModel: outer if:")
                _isFollowingStatus.value = Status.ERROR("Error: $errorString")
            }
        }
    }


    private val _followStatus = MutableLiveData<Status<MessageResponse>>()
    val followStatus: LiveData<Status<MessageResponse>> = _followStatus

    fun follow(followerId: Long, followingId: Long) {
        viewModelScope.launch {
            val response = profileRepository.follow(followerId, followingId)
            val errorString = response.errorBody()?.string()

            if (response.isSuccessful) {
                val body = response.body()

                if (body != null) {
                    _followStatus.value = Status.SUCCESS(body)
                } else {
                    Log.d("TAG", "follow: profileViewModel: inner if:")
                    _followStatus.value = Status.ERROR("Error: $errorString")
                }
            } else {
                Log.d("TAG", "follow: profileViewModel: outer if:")
                _followStatus.value = Status.ERROR("Error: $errorString")
            }
        }
    }

    private val _unfollowStatus = MutableLiveData<Status<MessageResponse>>()
    val unfollowStatus: LiveData<Status<MessageResponse>> = _unfollowStatus

    fun unfollow(followerId: Long, followingId: Long) {
        viewModelScope.launch {
            val response = profileRepository.unfollow(followerId, followingId)
            val errorString = response.errorBody()?.string()

            if (response.isSuccessful) {
                val body = response.body()

                if (body != null) {
                    _unfollowStatus.value = Status.SUCCESS(body)
                } else {
                    Log.d("TAG", "unfollow: profileViewModel: inner if:")
                    _unfollowStatus.value = Status.ERROR("Error: $errorString")
                }
            } else {
                Log.d("TAG", "unfollow: profileViewModel: outer if:")
                _unfollowStatus.value = Status.ERROR("Error: $errorString")
            }
        }
    }

    private val _followerStatus = MutableLiveData<Status<List<FollowRelation>>>()
    val followerStatus: LiveData<Status<List<FollowRelation>>> = _followerStatus

    fun getFollowers(userId: Long) {
        viewModelScope.launch {
            val response = profileRepository.getFollowers(userId)
            val errorString = response.errorBody()?.string()

            if (response.isSuccessful) {
                val body = response.body()

                if (body != null) {
                    _followerStatus.value = Status.SUCCESS(body)
                } else {
                    Log.d("TAG", "getFollowers: profileViewModel: inner if:")
                    _followerStatus.value = Status.ERROR("Error: $errorString")
                }
            } else {
                Log.d("TAG", "getFollowers: profileViewModel: outer if:")
                _followerStatus.value = Status.ERROR("Error: $errorString")
            }
        }
    }


    private val _followingStatus = MutableLiveData<Status<List<FollowRelation>>>()
    val followingStatus: LiveData<Status<List<FollowRelation>>> = _followingStatus

    fun getFollowing(userId: Long) {
        viewModelScope.launch {
            val response = profileRepository.getFollowing(userId)
            val errorString = response.errorBody()?.string()

            if (response.isSuccessful) {
                val body = response.body()

                if (body != null) {
                    _followingStatus.value = Status.SUCCESS(body)
                } else {
                    Log.d("TAG", "getFollowing: profileViewModel: inner if:")
                    _followingStatus.value = Status.ERROR("Error: $errorString")
                }
            } else {
                Log.d("TAG", "getFollowing: profileViewModel: outer if:")
                _followingStatus.value = Status.ERROR("Error: $errorString")
            }
        }
    }
}