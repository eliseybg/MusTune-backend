package org.mustune.routes.song

import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json
import org.koin.ktor.ext.get
import org.mustune.domain.model.Song
import org.mustune.domain.repository.FilesRepository
import org.mustune.domain.repository.SongsRepository
import org.mustune.entities.SongInfoBody
import org.mustune.plugins.JwtConfig
import org.mustune.util.exceptions.UnknownServerException
import java.io.File

fun Route.addSong() {
    post("/addSong") {
        val userId = with(JwtConfig) { call.getId() }
        val parts = call.receiveMultipart().readAllParts()
        val fileItem = parts.firstOrNull { it is PartData.FileItem } as? PartData.FileItem
        fileItem ?: throw BadRequestException("No file")
        val formItem = parts.firstOrNull { it is PartData.FormItem } as? PartData.FormItem
        formItem ?: throw BadRequestException("No file info")

        val songInfo = Json.decodeFromString(SongInfoBody.serializer(), formItem.value)

        val songRepository = context.get<SongsRepository>()
        val song = Song(
            title = songInfo.title,
            artist = songInfo.artist,
            isDownloadable = songInfo.isDownloadable,
            shareType = songInfo.shareType,
            isFavourite = false,
            isShared = false,
            isCreator = true,
            createdBy = userId
        )
        val addedSong = songRepository.addSong(userId, song) ?: throw UnknownServerException()
        val name = fileItem.originalFileName?.substringAfterLast(".").orEmpty()
        val file = File("uploads/${addedSong.id}.$name").also { it.createNewFile() }
        fileItem.streamProvider().use { its ->
            file.outputStream().buffered().use { its.copyTo(it) }
        }
        val filesRepository = context.get<FilesRepository>()
        filesRepository.addFile(addedSong.id, file.path)
        call.respond(message = addedSong, status = HttpStatusCode.OK)
    }
}