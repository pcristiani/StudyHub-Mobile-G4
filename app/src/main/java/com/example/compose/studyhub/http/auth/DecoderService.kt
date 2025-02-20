package com.example.compose.studyhub.http.auth

import com.auth0.android.jwt.JWT
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun decodeJWT(token: String): LoginResponse? {
    try {
        val jwt = JWT(token)
        val idUsuario = jwt.getClaim("id").asInt()
        val cedula = jwt.getClaim("cedula").asString()
        val rol = jwt.getClaim("rol").asString()

        println("ID: $idUsuario")
        println("CI: $cedula")
        println("Rol: $rol")

        return LoginResponse(
            idUsuario, cedula, rol
        )

    } catch (e: Exception) {
        e.printStackTrace()
        return null
    }
}

fun decodeUser(token: String): List<LoginResponse>? {
    return try {
        val jwt = JWT(token)
        val usersJson = jwt.getClaim("users").asString() ?: throw IllegalArgumentException("Users claim is missing or invalid")
        val gson = Gson()
        val userType = object : TypeToken<List<LoginResponse>>() {}.type
        gson.fromJson<List<LoginResponse>>(usersJson, userType)

    } catch (e: Exception) {
        e.printStackTrace()
        null
    } catch (e: IllegalArgumentException) {
        e.printStackTrace()
        null
    }
}

fun decodeSolicitudes(token: String): List<SolicitudRequest>? {
    return try {
        val jwt = JWT(token)
        val usersJson = jwt.getClaim("").asString() ?: throw IllegalArgumentException("Users claim is missing or invalid")
        val gson = Gson()
        val userType = object : TypeToken<List<SolicitudRequest>>() {}.type
        gson.fromJson<List<SolicitudRequest>>(usersJson, userType)

    } catch (e: Exception) {
        e.printStackTrace()
        null
    } catch (e: IllegalArgumentException) {
        e.printStackTrace()
        null
    }
}

data class SolicitudRequest(val idCarrera: Int, val nombre: String, val descripcion: String, val requisitos: String, val duracion: Int)

data class LoginResponse(val idUsuario: Int?, val cedula: String?, val rol: String?)