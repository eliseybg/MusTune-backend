package org.mustune.routes.music

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.get
import org.mustune.domain.repository.SongsRepository
import org.mustune.plugins.JwtConfig
import org.mustune.util.extentions.page
import org.mustune.util.extentions.pageSize
import org.mustune.util.extentions.tab

fun Route.allSongs() {
    get("/allSongs") {
        val userId = with(JwtConfig) { call.getId() }

        val page = call.request.queryParameters.page
        val pageSize = call.request.queryParameters.pageSize
        val tab = call.request.queryParameters.tab

        val songRepository = context.get<SongsRepository>()
        call.respond(message = songRepository.getAllSongs(userId, tab, page, pageSize), status = HttpStatusCode.OK)
    }
}