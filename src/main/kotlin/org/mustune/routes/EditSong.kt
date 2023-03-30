package org.mustune.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.get
import org.mustune.entities.SongInfoBody
import org.mustune.repository.song.SongRepository

fun Route.editSong() {
    post("/editSong") {
        try {
            val body = call.receive<SongInfoBody>()
            require(body.id != null) { "id shouldn't be null" }

            val songRepository = context.get<SongRepository>()
            val song = songRepository.getSong(body.id)
                .copy(
                    title = body.title,
                    artist = body.artist,
                    isDownloadable = body.isDownloadable,
                    shareType = body.shareType
                )
            songRepository.editSong(song)
            call.respond(message = song, status = HttpStatusCode.OK)
        } catch (exception: IllegalArgumentException) {
            call.respond(status = HttpStatusCode.BadRequest, message = exception.message.orEmpty())
        }
    }
}