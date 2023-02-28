package org.mustune.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.defaultheaders.*
import kotlin.time.Duration.Companion.days

fun Application.configureDefaultHeader() {
    install(DefaultHeaders) {
        val oneYearInSeconds = 365.days.inWholeSeconds
        header(
            name = HttpHeaders.CacheControl,
            value = "public, max-age=$oneYearInSeconds, immutable"
        )
    }
}