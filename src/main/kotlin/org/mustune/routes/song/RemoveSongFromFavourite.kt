package org.mustune.routes.song

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.get
import org.mustune.domain.repository.SongsRepository
import org.mustune.entities.SongIdBody
import org.mustune.plugins.JwtConfig
import java.util.*

fun Route.removeSongFromFavourite() {
    post("/removeSongFromFavourite") {
        val userId = with(JwtConfig) { call.getId() }
        val body = call.receive<SongIdBody>()
        val songId = UUID.fromString(body.songId)

        val songRepository = context.get<SongsRepository>()
        val song = songRepository.removeSongFromFavourite(userId, songId) ?: throw NotFoundException()
        call.respond(message = song, status = HttpStatusCode.OK)
    }
}