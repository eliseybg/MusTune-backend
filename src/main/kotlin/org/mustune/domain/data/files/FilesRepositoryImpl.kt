package org.mustune.domain.data.files

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.mustune.domain.data.dbQuery
import org.mustune.domain.mapper.toFile
import org.mustune.domain.model.File
import org.mustune.domain.repository.FilesRepository
import java.util.UUID

class FilesRepositoryImpl : FilesRepository {
    override suspend fun addFile(songId: UUID, filePath: String): Unit = dbQuery {
        Files.insert {
            it[Files.songId] = songId
            it[Files.filePath] = filePath
        }
    }

    override suspend fun getFile(songId: UUID): File? = dbQuery {
        Files.select { Files.songId eq songId }
            .orderBy(Files.createdAt, order = SortOrder.DESC)
            .map(ResultRow::toFile)
            .singleOrNull()
    }
}