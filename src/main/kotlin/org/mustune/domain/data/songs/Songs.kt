package org.mustune.domain.data.songs

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.datetime
import org.mustune.domain.data.users.Users
import org.mustune.entities.ShareType

object Songs : UUIDTable() {
    val title = varchar("email", 128)
    val artist = varchar("username", 1024)
    val isDownloadable = bool("isDownloadable")
    val shareType = enumeration<ShareType>("shareType")
    val createdAt = datetime("createdAt")
    val updatedAt = datetime("updatedAt")
    val createdBy = uuid("createdBy").references(Users.id).nullable()
}