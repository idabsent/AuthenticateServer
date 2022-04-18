package org.example.main

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.routing.routing
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
fun Application.configureRouting() {
    routing {

    }
}

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json()
    }
}