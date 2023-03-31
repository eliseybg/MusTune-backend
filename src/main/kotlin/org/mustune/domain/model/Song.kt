package org.mustune.domain.model

import org.mustune.entities.ShareType
import java.util.*

data class Song(
    val title: String,
    val artist: String,
    val isDownloadable: Boolean,
    val isFavourite: Boolean,
    val isShared: Boolean,
    val isCreator: Boolean,
    val shareType: ShareType,
    val createdAt: Date = Calendar.getInstance().time,
    val updatedAt: Date = Calendar.getInstance().time,
    val createdBy: UUID?,
    val id: UUID = UUID.randomUUID()
)