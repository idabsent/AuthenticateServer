package org.example.main

import io.ktor.server.routing.routing

import io.ktor.server.application.Application
import io.ktor.server.application.install

import io.ktor.server.plugins.contentnegotiation.ContentNegotiation

import io.ktor.serialization.kotlinx.json.json

import kotlinx.serialization.json.Json

import org.example.main.routs.authLogin
import org.example.main.routs.jwksResource
import org.example.main.routs.signUp

fun Application.configureRouting() {
    routing {
        authLogin()
        jwksResource()
        signUp()
    }
}

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json(Json{ignoreUnknownKeys = true})
    }
}