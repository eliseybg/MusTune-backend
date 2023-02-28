package org.mustune.entities

import kotlinx.serialization.Serializable

@Serializable
data class SongInfo(
    val id: String,
    val name: String,
    val author: String,
    val link: String,
)