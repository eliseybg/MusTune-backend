package org.mustune

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.*
import org.mustune.plugins.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
fun Application.module() {
    configuredKoin()
    configureRouting()
    configureMonitoring()
    configureSerialization()
    configureDefaultHeader()
}
