package org.mustune.domain.model

import java.util.*

data class User(
    val id: String,
    val username: String,
    val email: String,
    val password: String,
    val createdAt: Date,
    val updatedAt: Date
)