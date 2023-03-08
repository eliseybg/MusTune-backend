package org.mustune.plugins

import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.mustune.routes.getAllSongs
import org.mustune.routes.root
import org.mustune.routes.searchSongs

fun Application.configureRouting() {
    routing {
        root()
        getAllSongs()
        searchSongs()
    }
}
