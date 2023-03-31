package org.mustune.domain.model

import java.util.Date
import java.util.UUID

data class File(
    val id: UUID,
    val songId: UUID,
    val filepath: String,
    val createdAt: Date
)