package org.mustune.routes.song

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.get
import org.mustune.domain.repository.SongsRepository
import org.mustune.plugins.JwtConfig
import org.mustune.util.extentions.songId

fun Route.deleteSong() {
    delete("/deleteSong") {
        val userId = with(JwtConfig) { call.getId() }
        val songId = call.request.queryParameters.songId

        val songRepository = context.get<SongsRepository>()
        val song = songRepository.getSong(userId, songId) ?: throw NotFoundException()
        if (!song.isCreator) throw BadRequestException("no access")
        songRepository.deleteSong(songId)
        call.respond(message = "", status = HttpStatusCode.OK)
    }
}