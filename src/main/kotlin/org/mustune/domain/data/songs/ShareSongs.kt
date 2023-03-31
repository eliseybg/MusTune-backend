package org.mustune.domain.data.songs

import org.jetbrains.exposed.sql.Table
import org.mustune.domain.data.users.Users

object ShareSongs : Table() {
    val userId = uuid("userId").references(Users.id)
    val songId = uuid("songId").references(Songs.id)

    override val primaryKey = PrimaryKey(userId, songId)
}