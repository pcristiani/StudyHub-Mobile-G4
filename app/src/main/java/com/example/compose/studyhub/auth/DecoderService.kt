package com.example.compose.studyhub.auth

import com.auth0.android.jwt.JWT

fun decodeJWT(token: String) {
    try {
        val jwt = JWT(token)

        // Acceder a los claims
        val id = jwt.getClaim("id").asInt()
        val ci = jwt.getClaim("ci").asString()
        val rol = jwt.getClaim("rol").asString()

        println("ID: $id")
        println("CI: $ci")
        println("Rol: $rol")

    } catch (e: Exception) {
        e.printStackTrace()
    }
}