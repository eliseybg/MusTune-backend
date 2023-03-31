package org.mustune.domain.repository

import org.mustune.domain.model.File
import java.util.UUID

interface FilesRepository {
    suspend fun addFile(songId: UUID, filePath: String)
    suspend fun getFile(songId: UUID): File?
}