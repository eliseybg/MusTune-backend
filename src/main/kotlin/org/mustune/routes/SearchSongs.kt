package org.mustune.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.get
import org.mustune.entities.SearchSongsBody
import org.mustune.repository.song.SongRepository

fun Route.searchSongs() {
    post("/searchSongs") {
        try {
            val body = call.receive<SearchSongsBody>()
            val searchText = body.searchText
            val searchFilter = body.searchFilter
            val page = body.page
            val pageSize = body.pageSize

            val songRepository = context.get<SongRepository>()
            call.respond(message = songRepository.searchSongs(searchText, searchFilter, page, pageSize), status = HttpStatusCode.OK)
        } catch (exception: NumberFormatException) {
            call.respond(status = HttpStatusCode.BadRequest, message = "Only numbers allowed")
        } catch (exception: IllegalArgumentException) {
            call.respond(status = HttpStatusCode.BadRequest, message = exception.message.orEmpty())
        }
    }
}