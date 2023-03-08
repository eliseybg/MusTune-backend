package org.mustune.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.get
import org.mustune.entities.MusicTab
import org.mustune.repository.song.SongRepository
import java.util.UUID
import kotlin.math.min

const val MAX_PAGE_SIZE = 100
const val DEFAULT_PAGE_SIZE = 100
const val INITIAL_PAGE = 1

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

private val Parameters.page: Int
    get() {
        val page = this["page"]?.toInt() ?: INITIAL_PAGE
        require(page >= INITIAL_PAGE) { "Page should be >= $INITIAL_PAGE" }
        return page
    }

private val Parameters.pageSize: Int
    get() {
        val pageSize = min(this["pageSize"]?.toInt() ?: DEFAULT_PAGE_SIZE, MAX_PAGE_SIZE)
        require(pageSize >= 0) { "Page size should be >= 0" }
        return pageSize
    }


private val Parameters.tab: MusicTab
    get() {
        val tab = this["tab"] ?: return MusicTab.EXPLORE
        require(MusicTab.values().any { it.name == tab }) { "Tab should be one of " + MusicTab.values().joinToString() }
        return MusicTab.valueOf(tab)
    }

