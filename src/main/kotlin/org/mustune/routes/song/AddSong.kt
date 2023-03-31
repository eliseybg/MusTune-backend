package org.mustune.routes.song

import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json
import org.koin.ktor.ext.get
import org.mustune.domain.model.Song
import org.mustune.domain.repository.SongsRepository
import org.mustune.entities.SongInfoBody
import org.mustune.plugins.JwtConfig
import java.io.File

fun Route.addSong() {
    post("/addSong") {
        val userId = with(JwtConfig) { call.getId() }
        val parts = call.receiveMultipart().readAllParts()
        val fileItem = parts.firstOrNull { it is PartData.FileItem } as? PartData.FileItem
        val formItem = parts.firstOrNull { it is PartData.FormItem } as? PartData.FormItem

        val name = fileItem?.originalFileName!!
        val file = File("uploads/$name").also { it.createNewFile() }

        fileItem.streamProvider().use { its ->
            file.outputStream().buffered().use { its.copyTo(it) }
        }
        val songInfo = Json.decodeFromString(SongInfoBody.serializer(), formItem?.value.orEmpty())

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
        songRepository.addSong(userId, song)
        call.respond(message = song, status = HttpStatusCode.OK)
    }
}