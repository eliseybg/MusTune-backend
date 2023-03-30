package org.mustune.entities

import kotlinx.serialization.Serializable

@Serializable
data class SongEntity(
    val id: String,
    val title: String,
    val artist: String,
    val link: String,
    val tab: MusicTab,
    val isDownloadable: Boolean,
    val shareType: ShareType,
    val createdAt: Long = System.currentTimeMillis()
)