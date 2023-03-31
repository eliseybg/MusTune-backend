package org.mustune.domain.data.users

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.datetime

object Users : UUIDTable() {
    val email = varchar("email", 128)
    val username = varchar("username", 1024)
    val password = varchar("password", 1024)
    val createdAt = datetime("createdAt")
    val updatedAt = datetime("updatedAt")
}