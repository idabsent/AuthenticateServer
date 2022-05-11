package org.example.main.routs

import com.auth0.jwk.JwkProviderBuilder
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call

import io.ktor.server.routing.Route
import io.ktor.server.routing.route
import io.ktor.server.routing.post
import io.ktor.server.request.receiveOrNull

import io.ktor.server.response.respond
import io.ktor.server.response.respondText

import org.example.main.models.*

import java.security.KeyFactory
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.security.spec.PKCS8EncodedKeySpec

import java.util.Date
import java.util.Base64
import java.util.concurrent.TimeUnit

val Route.userRepository : UserRepository
    get() = Users

fun Route.authLogin() {
    val userRepository = userRepository

    route("auth") {
        post("login") {
            val user = call.receiveOrNull<User>() ?: return@post call.respondText(
                "Missing body",
                status = HttpStatusCode.BadRequest,
            )

            if (user.password == null || user.nickname == null) return@post call.respondText(
                "Bad authentication",
                status = HttpStatusCode.Unauthorized,
            )

            val userInRepository = userRepository.findByNickname(user.nickname)

            if (userInRepository == null || user.password != userInRepository.password)
                return@post call.respondText(
                    "Bad authentication",
                    status = HttpStatusCode.Unauthorized
                )

            val issuer = call.application.environment.config.property("jwt.issuer").getString()
            val privateKey = call.application.environment.config.property("jwt.privateKey").getString()

            val jwkProvider = JwkProviderBuilder(issuer)
                .cached(10, 24, TimeUnit.HOURS)
                .rateLimited(10, 1, TimeUnit.HOURS)
                .build()

            val publicKey = jwkProvider.get("ffa5009f-f815-4d87-a36b-e4c29e5829b5").publicKey
            val base64Key = PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey))
            val pKey = KeyFactory.getInstance("RSA").generatePrivate(base64Key)

            val token = JWT.create()
                .withIssuer(issuer)
                .withExpiresAt(Date(System.currentTimeMillis() + 60000))
                .sign(Algorithm.RSA256(publicKey as RSAPublicKey, pKey as RSAPrivateKey))

            call.respond(hashMapOf("token" to token))
        }
    }
}

fun Route.signUp() {
    val userRepository = userRepository

    route("auth") {
        post("signup") {
            val user = call.receiveOrNull<User>() ?: return@post call.respondText(
                "Missing body",
                status = HttpStatusCode.BadRequest,
            )

            if (user.haveNull()) return@post call.respondText(
                "Missing one or more parameters",
                status = HttpStatusCode.BadRequest,
            )

            if (userRepository.contains(user)) return@post call.respondText(
                "User exist",
                status = HttpStatusCode.Conflict,
            )

            userRepository.appendUser(user)

            call.respondText("Success", status = HttpStatusCode.Created)
        }
    }
}