package org.mustune.domain.mapper

import org.jetbrains.exposed.sql.ResultRow
import org.mustune.domain.data.files.Files
import org.mustune.domain.model.File
import org.mustune.util.extentions.toDate

fun ResultRow.toFile() = File(
    id = this[Files.id].value,
    songId = this[Files.songId],
    filepath = this[Files.filePath],
    createdAt = this[Files.createdAt].toDate()
)