package org.mustune.domain.data.files

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.datetime
import org.mustune.domain.data.songs.Songs

object Files : UUIDTable() {
    val songId = uuid("songId").references(Songs.id)
    val filePath = varchar("filePath", 1024)
    val createdAt = datetime("createdAt")
}