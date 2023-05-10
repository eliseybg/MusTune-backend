package org.mustune.routes.song

import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.get
import org.mustune.domain.repository.FilesRepository
import org.mustune.domain.repository.SongsRepository
import org.mustune.plugins.JwtConfig
import org.mustune.util.extentions.songId
import java.io.File as LocalFile

fun Route.downloadSong() {
    get("/downloadSong") {
        val userId = with(JwtConfig) { call.getId() }
        val songId = call.request.queryParameters.songId
        val songsRepository = context.get<SongsRepository>()
        val filesRepository = context.get<FilesRepository>()
        val song = songsRepository.getSong(userId, songId) ?: throw NotFoundException()
        if (!song.isDownloadable && !song.isCreator) throw BadRequestException("No access to song")
        val file = filesRepository.getFile(songId) ?: throw NotFoundException()

        val filename = "${song.title} - ${song.artist}"
        call.response.header("Content-Disposition", "attachment; filename=\"$filename\"")
        call.respondFile(LocalFile(file.filepath))
    }
}