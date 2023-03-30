package org.mustune.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.get
import org.mustune.repository.song.SongRepository
import org.mustune.util.extentions.songId

fun Route.deleteSong() {
    delete("/deleteSong") {
        try {
            val songId = call.request.queryParameters.songId

            val songRepository = context.get<SongRepository>()
            songRepository.deleteSong(songId)
            call.respond(message = "", status = HttpStatusCode.OK)
        } catch (exception: IllegalArgumentException) {
            call.respond(status = HttpStatusCode.BadRequest, message = exception.message.orEmpty())
        }
    }
}