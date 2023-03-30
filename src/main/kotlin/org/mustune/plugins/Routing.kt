package org.mustune.plugins

import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.mustune.routes.*

fun Application.configureRouting() {
    routing {
        root()
        getSong()
        addSong()
        editSong()
        deleteSong()
        getAllSongs()
        searchSongs()
    }
}
