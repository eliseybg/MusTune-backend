package org.mustune.routes.user

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.get
import org.mustune.domain.repository.UsersRepository
import org.mustune.plugins.JwtConfig

fun Route.deleteAccount() {
    delete("/deleteAccount") {
        val userId = with(JwtConfig) { call.getId() }
        val usersRepository = context.get<UsersRepository>()
        call.respond(message = usersRepository.deleteUser(userId), status = HttpStatusCode.OK)
    }
}