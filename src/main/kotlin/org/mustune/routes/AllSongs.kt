package org.mustune.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.get
import org.mustune.repository.song.SongRepository

fun Route.getAllSongs() {
    get("/allSongs") {
        try {
            val page = call.request.queryParameters["page"]?.toInt() ?: 0
            require(page >= 0)
            val songRepository = context.get<SongRepository>()
            call.respond(message = songRepository.getAllSongs(page), status = HttpStatusCode.OK)
        } catch (exception: NumberFormatException) {
            call.respond(status = HttpStatusCode.BadRequest, message = "Only numbers allowed")
        } catch (exception: IllegalArgumentException) {
            call.respond(status = HttpStatusCode.BadRequest, message = "Number should be >= 0")
        }
    }
}