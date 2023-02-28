package org.mustune.plugins

import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.mustune.routes.getAllSongs
import org.mustune.routes.root

fun Application.configureRouting() {
    routing {
        root()
        getAllSongs()
    }
}
