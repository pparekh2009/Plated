package com.priyanshparekh.core.model.auth

data class UserLoginResponse(
    val id: Long,
    val displayName: String,
    val bio: String,
    val profession: String,
    val website: String,
    val jwtToken: String
)
