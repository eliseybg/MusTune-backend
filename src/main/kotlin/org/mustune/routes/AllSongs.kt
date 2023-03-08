package org.mustune.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.get
import org.mustune.repository.song.SongRepository
import org.mustune.util.extentions.page
import org.mustune.util.extentions.pageSize
import org.mustune.util.extentions.tab

fun Route.getAllSongs() {
    get("/allSongs") {
        try {
            val page = call.request.queryParameters.page
            val pageSize = call.request.queryParameters.pageSize
            val tab = call.request.queryParameters.tab

            val songRepository = context.get<SongRepository>()
            call.respond(message = songRepository.getAllSongs(tab, page, pageSize), status = HttpStatusCode.OK)
        } catch (exception: NumberFormatException) {
            call.respond(status = HttpStatusCode.BadRequest, message = "Only numbers allowed")
        } catch (exception: IllegalArgumentException) {
            call.respond(status = HttpStatusCode.BadRequest, message = exception.message.orEmpty())
        }
    }
}