package com.priyanshparekh.feature.auth

import com.priyanshparekh.core.model.auth.UserLoginRequest
import com.priyanshparekh.core.model.auth.UserSignUpRequest
import com.priyanshparekh.core.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepository {

    suspend fun signUp(userSignUpRequest: UserSignUpRequest) = withContext(Dispatchers.IO) {
        return@withContext RetrofitInstance.api.signUp(userSignUpRequest)
    }

    suspend fun login(userLoginRequest: UserLoginRequest) = withContext(Dispatchers.IO) {
        return@withContext RetrofitInstance.api.login(userLoginRequest)
    }

}