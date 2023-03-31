package org.mustune.domain.mapper

import org.jetbrains.exposed.sql.ResultRow
import org.mustune.domain.data.users.Users
import org.mustune.domain.model.User
import org.mustune.util.extentions.toDate

fun ResultRow.toUser() = User(
    id = this[Users.id].value.toString(),
    email = this[Users.email],
    username = this[Users.username],
    password = this[Users.password],
    createdAt = this[Users.createdAt].toDate(),
    updatedAt = this[Users.updatedAt].toDate(),
)