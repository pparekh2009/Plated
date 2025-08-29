package com.priyanshparekh.feature.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

}