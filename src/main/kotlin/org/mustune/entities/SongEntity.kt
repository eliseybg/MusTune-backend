package org.mustune.entities

import kotlinx.serialization.Serializable

@Serializable
data class SongEntity(
    val id: String,
    val title: String,
    val author: String,
    val link: String,
    val tab: MusicTab,
    val createdAt: Long = System.currentTimeMillis()
)