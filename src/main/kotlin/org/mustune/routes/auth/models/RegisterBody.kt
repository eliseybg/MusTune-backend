package org.mustune.routes.auth.models

import kotlinx.serialization.Serializable

@Serializable
data class RegisterBody(
    val email: String,
    val username: String,
    val password: String
)