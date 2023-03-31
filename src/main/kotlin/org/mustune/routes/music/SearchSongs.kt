package org.mustune.routes.music

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.get
import org.mustune.domain.repository.SongsRepository
import org.mustune.entities.SearchSongsBody
import org.mustune.plugins.JwtConfig

fun Route.searchSongs() {
    post("/searchSongs") {
        val userId = with(JwtConfig) { call.getId() }

        val body = call.receive<SearchSongsBody>()
        val searchText = body.searchText
        val searchFilter = body.searchFilter
        val page = body.page
        val pageSize = body.pageSize

        val songRepository = context.get<SongsRepository>()
        call.respond(
            message = songRepository.searchSongs(userId, searchText, searchFilter, page, pageSize),
            status = HttpStatusCode.OK
        )
    }
}