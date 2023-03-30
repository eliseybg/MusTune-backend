package org.mustune.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.get
import org.mustune.repository.song.SongRepository
import org.mustune.util.extentions.page
import org.mustune.util.extentions.pageSize
import org.mustune.util.extentions.songId
import org.mustune.util.extentions.tab

fun Route.getSong() {
    get("/song") {
        try {
            val songId = call.request.queryParameters.songId

            val songRepository = context.get<SongRepository>()
            val song = songRepository.getSong(songId)
            if (song != null) call.respond(message = song, status = HttpStatusCode.OK)
            else call.respond(status = HttpStatusCode.NotFound, message = "Song not found")
        } catch (exception: NumberFormatException) {
            call.respond(status = HttpStatusCode.BadRequest, message = "Only numbers allowed")
        } catch (exception: IllegalArgumentException) {
            call.respond(status = HttpStatusCode.BadRequest, message = exception.message.orEmpty())
        }
    }
}