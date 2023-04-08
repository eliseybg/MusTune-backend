package org.mustune

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.*
import org.mustune.plugins.*
import org.mustune.plugins.serialization.configureSerialization
import java.util.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
fun Application.module() {
    configureDb()
    configureJwt()
    configuredKoin()
    configureRouting()
    configureMonitoring()
    configureSerialization()
    configureDefaultHeader()
    configureCommonExceptionHandler()
}
