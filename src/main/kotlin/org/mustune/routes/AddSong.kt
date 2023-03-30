package org.mustune.routes

import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json
import org.koin.ktor.ext.get
import org.mustune.entities.MusicTab
import org.mustune.entities.SongEntity
import org.mustune.entities.SongInfoBody
import org.mustune.repository.song.SongRepository
import java.io.File
import java.util.UUID

fun Route.addSong() {
    post("/addSong") {
        try {
            val parts = call.receiveMultipart().readAllParts()
            val fileItem = parts.firstOrNull { it is PartData.FileItem } as? PartData.FileItem
            val formItem = parts.firstOrNull { it is PartData.FormItem } as? PartData.FormItem

            val name = fileItem?.originalFileName!!
            val file = File("uploads/$name").also { it.createNewFile() }

            fileItem.streamProvider().use { its ->
                file.outputStream().buffered().use { its.copyTo(it) }
            }
            val songInfo = Json.decodeFromString(SongInfoBody.serializer(), formItem?.value.orEmpty())

            val songRepository = context.get<SongRepository>()
            val song = SongEntity(
                id = UUID.randomUUID().toString(),
                title = songInfo.title,
                artist = songInfo.artist,
                link = "",
                tab = MusicTab.EXPLORE,
                isDownloadable = songInfo.isDownloadable,
                shareType = songInfo.shareType
            )
            songRepository.addSong(song)
            call.respond(message = song, status = HttpStatusCode.OK)
        } catch (exception: IllegalArgumentException) {
            call.respond(status = HttpStatusCode.BadRequest, message = exception.message.orEmpty())
        }
    }
}