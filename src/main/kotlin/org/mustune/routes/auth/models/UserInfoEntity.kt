package org.mustune.routes.auth.models

import kotlinx.serialization.Serializable

@Serializable
data class UserInfoEntity(
    val username: String,
    val email: String,
    val registerToken: String,
    val refreshToken: String
)