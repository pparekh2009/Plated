package com.priyanshparekh.core.model.auth

import java.io.Serializable

data class UserSignUpRequest(
    val email: String,
    val password: String,
    var displayName: String? = null,
    var bio: String? = null,
    var profession: String? = null,
    var website: String? = null,
): Serializable {

    override fun toString(): String {
        return "User: Email: $email, Name: $displayName"
    }

}
