package org.mustune.plugins

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*
import org.mustune.routes.auth.login
import org.mustune.routes.auth.signup
import org.mustune.routes.user.deleteAccount
import org.mustune.routes.music.allSongs
import org.mustune.routes.music.searchSongs
import org.mustune.routes.music.songsCategories
import org.mustune.routes.root
import org.mustune.routes.song.*

fun Application.configureRouting() {
    routing {
        root()
        login()
        signup()
        authenticate {
            deleteAccount()
            getSong()
            addSong()
            editSong()
            deleteSong()
            downloadSong()
            addSongToFavourite()
            removeSongFromFavourite()
            allSongs()
            searchSongs()
            songsCategories()
        }
    }
}
