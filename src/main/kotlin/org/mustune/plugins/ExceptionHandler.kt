package org.mustune.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import org.mustune.util.exceptions.UnauthorizedException

fun Application.configureCommonExceptionHandler() {
    install(StatusPages) {
        exception<NotFoundException> { call, cause ->
            call.respond(
                message = cause.message.orEmpty(),
                status = HttpStatusCode.NotFound
            )
        }
        exception<UnauthorizedException> { call, cause ->
            call.respond(
                message = cause.message.orEmpty(),
                status = HttpStatusCode.Unauthorized
            )
        }
        exception<BadRequestException> { call, cause ->
            call.respond(
                message = cause.message.orEmpty(),
                status = HttpStatusCode.BadRequest
            )
        }
        exception<Exception> { call, cause ->
            call.respond(
                message = cause.message.orEmpty(),
                status = HttpStatusCode.InternalServerError
            )
        }
    }
}
