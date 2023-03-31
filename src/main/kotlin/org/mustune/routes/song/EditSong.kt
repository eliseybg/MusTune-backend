package org.mustune.routes.song

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.get
import org.mustune.domain.repository.SongsRepository
import org.mustune.entities.SongInfoBody
import org.mustune.plugins.JwtConfig
import java.util.*

fun Route.editSong() {
    post("/editSong") {
        try {
            val userId = with(JwtConfig) { call.getId() }
            val body = call.receive<SongInfoBody>()
            require(body.id != null) { "id shouldn't be null" }

            val songRepository = context.get<SongsRepository>()
            val song = songRepository.getSong(UUID.fromString(body.id)) ?: throw NotFoundException()
            val newSong = song.copy(
                    title = body.title,
                    artist = body.artist,
                    isDownloadable = body.isDownloadable,
                    shareType = body.shareType
                )
            songRepository.editSong(userId, newSong)
            call.respond(message = song, status = HttpStatusCode.OK)
        } catch (exception: IllegalArgumentException) {
            call.respond(status = HttpStatusCode.BadRequest, message = exception.message.orEmpty())
        }
    }
}