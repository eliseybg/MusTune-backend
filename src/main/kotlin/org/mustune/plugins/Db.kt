package org.mustune.plugins

import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.mustune.domain.data.files.Files
import org.mustune.domain.data.songs.FavouriteSongs
import org.mustune.domain.data.songs.ShareSongs
import org.mustune.domain.data.songs.Songs
import org.mustune.domain.data.users.Users

fun Application.configureDb() {
    val driverClassName = "org.h2.Driver"
    val jdbcURL = "jdbc:h2:file:./build/db"
    val database = Database.connect(jdbcURL, driverClassName)
    transaction(database) {
        SchemaUtils.create(Users)
        SchemaUtils.create(Songs)
        SchemaUtils.create(FavouriteSongs)
        SchemaUtils.create(ShareSongs)
        SchemaUtils.create(Files)
    }
}