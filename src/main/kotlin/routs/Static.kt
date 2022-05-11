package org.example.main.routs

import io.ktor.server.http.content.static
import io.ktor.server.http.content.resource

import io.ktor.server.routing.Route

fun Route.jwksResource() {
    static(".well-known") {
        resource("jwks.json")
    }
}