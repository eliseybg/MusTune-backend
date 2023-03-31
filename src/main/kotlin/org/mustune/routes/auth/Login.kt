package org.mustune.routes.auth

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.get
import org.mindrot.jbcrypt.BCrypt
import org.mustune.routes.auth.models.LoginBody
import org.mustune.util.exceptions.UnauthorizedException
import org.mustune.plugins.JwtConfig
import org.mustune.domain.repository.UsersRepository
import org.mustune.routes.auth.models.UserInfoEntity

fun Route.login() {
    post("/login") {
        val registerBody = runCatching {
            call.receiveNullable<LoginBody>()
        }.getOrNull() ?: throw BadRequestException("wrong body")
        val usersRepository = context.get<UsersRepository>()
        val user = usersRepository.getUserByEmailOrUsername(registerBody.emailOrUsername)
        user ?: throw NotFoundException("no such user")
        if (!BCrypt.checkpw(registerBody.password, user.password)) throw UnauthorizedException("wrong password")

        val registerToken = JwtConfig.makeToken(user.id)
        val refreshToken = JwtConfig.makeToken(user.id)

        val userInfo = UserInfoEntity(
            user.username,
            user.email,
            registerToken,
            refreshToken
        )

        call.respond(message = userInfo, status = HttpStatusCode.OK)
    }
}