package org.mustune.routes.music

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.get
import org.mustune.domain.repository.SongsRepository
import org.mustune.plugins.JwtConfig

fun Route.songsCategories() {
    get("/songsCategories") {
        val userId = with(JwtConfig) { call.getId() }
        val songRepository = context.get<SongsRepository>()
        call.respond(message = songRepository.getSongsCategories(userId), status = HttpStatusCode.OK)
    }
}