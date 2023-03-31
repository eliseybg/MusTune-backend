package org.mustune.routes.auth.models

import kotlinx.serialization.Serializable

@Serializable
data class LoginBody(
    val emailOrUsername: String,
    val password: String
)