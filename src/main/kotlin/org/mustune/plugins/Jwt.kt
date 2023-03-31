package org.mustune.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import org.mustune.util.exceptions.UnauthorizedException
import java.time.Instant
import java.util.*

fun Application.configureJwt() {
    install(Authentication) {
        jwt {
            verifier(JwtConfig.verifier)
            realm = "ktor.io"
            validate { credential ->
                if (credential.payload.audience.contains(JwtConfig.audience)) JWTPrincipal(credential.payload)
                else null
            }
        }
    }
}

object JwtConfig {
    private const val secret = "ktor-secret"
    private const val issuer = "ktor.io"
    private const val validityInMs = 36_000_00 * 24 // 24 hours
    private val algorithm = Algorithm.HMAC256(secret)

    val verifier: JWTVerifier = JWT
        .require(algorithm)
        .withIssuer(issuer)
        .build()

    fun makeToken(id: String): String = JWT.create()
        .withSubject("Authentication")
        .withIssuer(issuer)
        .withAudience(audience)
        .withClaim(claim, id)
        .withExpiresAt(getExpiration())
        .sign(algorithm)

    fun ApplicationCall.getId(): UUID {
        val id = authentication.principal<JWTPrincipal>()?.payload?.getClaim(claim)?.asString()
        id ?: throw UnauthorizedException()
        return UUID.fromString(id)
    }

    fun isTokenValid(token: String) = runCatching {
        val verifier = JWT.require(algorithm).build()
        val decodedJWT: DecodedJWT = verifier.verify(token)
        !decodedJWT.expiresAt.before(Date.from(Instant.now()))
    }.getOrNull() ?: false

    private fun getExpiration() = Date(System.currentTimeMillis() + validityInMs)

    const val audience = "ktor-audience"
    const val claim = "id"
}