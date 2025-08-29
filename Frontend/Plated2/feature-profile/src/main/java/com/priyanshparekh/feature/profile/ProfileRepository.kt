package com.priyanshparekh.feature.profile

import com.priyanshparekh.core.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProfileRepository {

    suspend fun getUserProfile(userId: Long) = withContext(Dispatchers.IO) {
        return@withContext RetrofitInstance.api.getUserProfile(userId)
    }

}