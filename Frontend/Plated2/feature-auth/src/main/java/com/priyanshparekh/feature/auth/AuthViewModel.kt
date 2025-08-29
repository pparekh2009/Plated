package com.priyanshparekh.feature.auth

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.application
import androidx.lifecycle.viewModelScope
import com.priyanshparekh.core.model.auth.UserLoginRequest
import com.priyanshparekh.core.model.auth.UserLoginResponse
import com.priyanshparekh.core.model.auth.UserSignUpRequest
import com.priyanshparekh.core.network.Status
import com.priyanshparekh.core.utils.Constants
import kotlinx.coroutines.launch

class AuthViewModel(application: Application): AndroidViewModel(application) {

    private val authRepository = AuthRepository()

    private val _signUpStatus = MutableLiveData<Status<UserSignUpRequest>>()
    val signUpStatus: LiveData<Status<UserSignUpRequest>> = _signUpStatus

    fun signUp(userSignUpRequest: UserSignUpRequest) {
        viewModelScope.launch {
            val response = authRepository.signUp(userSignUpRequest)

            if (response.isSuccessful) {
                val body = response.body()

                if (body != null) {
                    _signUpStatus.value = Status.SUCCESS(body)
                } else {
                    _signUpStatus.value = Status.ERROR("Error: ${response.errorBody()?.string()}")
                }
            } else {
                _signUpStatus.value = Status.ERROR("Error: ${response.errorBody()?.string()}")
            }
        }
    }


    private val _loginStatus = MutableLiveData<Status<UserLoginResponse>>()
    val loginStatus: LiveData<Status<UserLoginResponse>> = _loginStatus

    fun login(userLoginRequest: UserLoginRequest) {
        viewModelScope.launch {
            val response = authRepository.login(userLoginRequest)

            if (response.isSuccessful) {
                val body = response.body()

                if (body != null) {
                    _loginStatus.value = Status.SUCCESS(body)

                    application.applicationContext.getSharedPreferences(Constants.SharedPrefs.USER_DETAILS_PREF, Context.MODE_PRIVATE).edit().apply {
                        putLong(Constants.SharedPrefs.KEY_ID, body.id)
                        putString(Constants.SharedPrefs.KEY_DISPLAY_NAME, body.displayName)
                        putString(Constants.SharedPrefs.KEY_BIO, body.bio)
                        putString(Constants.SharedPrefs.KEY_PROFESSION, body.profession)
                        putString(Constants.SharedPrefs.KEY_WEBSITE, body.website)
                        putString(Constants.SharedPrefs.KEY_TOKEN, body.jwtToken)
                        apply()
                    }
                } else {
                    _loginStatus.value = Status.ERROR("Error: ${response.errorBody()?.string()}")
                }
            } else {
                _loginStatus.value = Status.ERROR("Error: ${response.errorBody()?.string()}")
            }
        }
    }


}