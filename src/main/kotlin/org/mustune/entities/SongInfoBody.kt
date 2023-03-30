package org.mustune.entities

import kotlinx.serialization.Serializable

@Serializable
data class SongInfoBody(
    val id: String? = null,
    val title: String,
    val artist: String,
    val isDownloadable: Boolean,
    val shareType: ShareType
)