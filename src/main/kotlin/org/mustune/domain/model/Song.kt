package org.mustune.domain.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.mustune.entities.ShareType
import java.util.*

@Serializable
data class Song(
    val title: String,
    val artist: String,
    val isDownloadable: Boolean,
    val isFavourite: Boolean,
    val isShared: Boolean,
    val isCreator: Boolean,
    val shareType: ShareType,
    @Contextual val createdAt: Date = Calendar.getInstance().time,
    @Contextual val updatedAt: Date = Calendar.getInstance().time,
    @Contextual val createdBy: UUID? = null,
    @Contextual val id: UUID = UUID.randomUUID()
)