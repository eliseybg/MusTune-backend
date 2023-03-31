package org.mustune.routes.auth

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.get
import org.mindrot.jbcrypt.BCrypt
import org.mustune.routes.auth.models.RegisterBody
import org.mustune.plugins.JwtConfig
import org.mustune.domain.repository.UsersRepository
import org.mustune.routes.auth.models.UserInfoEntity

fun Route.signup() {
    post("/signup") {
        val registerBody = call.receiveNullable<RegisterBody>() ?: throw BadRequestException("wrong body")
        val usersRepository = context.get<UsersRepository>()
        if (usersRepository.getUserByEmailOrUsername(registerBody.username) != null) throw BadRequestException("username taken")
        val salt = BCrypt.gensalt()
        val hashedPassword = BCrypt.hashpw(registerBody.password, salt)
        val user = usersRepository.addUser(registerBody.username, registerBody.email, hashedPassword)
        user ?: throw BadRequestException("Unknown exception")

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