package org.example.main

import io.ktor.server.netty.EngineMain
import io.ktor.server.application.Application

fun main(args: Array<String>) : Unit = EngineMain.main(args)

fun Application.module() {
    configureRouting()
    configureSerialization()
}