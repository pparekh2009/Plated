package com.priyanshparekh.core.model.auth

data class UserLoginRequest(
    val email: String,
    val password: String
)
