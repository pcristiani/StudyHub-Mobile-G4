package com.example.compose.studyhub.auth

import com.auth0.android.jwt.JWT
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun decodeJWT(token: String) {
    try {
        val jwt = JWT(token)

        // Acceder a los claims
        val id = jwt.getClaim("id").asInt()
        val cedula = jwt.getClaim("cedula").asString()
        val rol = jwt.getClaim("rol").asString()

        println("ID: $id")
        println("CI: $cedula")
        println("Rol: $rol")

    } catch (e: Exception) {
        e.printStackTrace()
    }
}


fun decodeUser(token: String): List<UserRequest>? {
    return try {
        val jwt = JWT(token)

        // Acceder a los claims
        val usersJson = jwt.getClaim("users").asString() ?: throw IllegalArgumentException("Users claim is missing or invalid")


        val gson = Gson()
        val userType = object : TypeToken<List<UserRequest>>() {}.type
        gson.fromJson<List<UserRequest>>(usersJson, userType)


    } catch (e: Exception) {
        e.printStackTrace()
        null
    } catch (e: IllegalArgumentException) {
        e.printStackTrace()
        null
    }
}

fun decodeSolicitudes(token: String): List<SolicitudRequest>?{
    return try {
        val jwt = JWT(token)

        val usersJson = jwt.getClaim("").asString() ?: throw IllegalArgumentException("Users claim is missing or invalid")


        val gson = Gson()
        val userType = object : TypeToken<List<SolicitudRequest>>() {}.type
        gson.fromJson<List<SolicitudRequest>>(usersJson, userType)


    }catch (e: Exception) {
        e.printStackTrace()
        null
    } catch (e: IllegalArgumentException) {
        e.printStackTrace()
        null
    }


}

data class SolicitudRequest(val idCarrera: Int, val nombre: String, val descripcion: String, val requisitos: String, val duracion: Int)

data class UserRequest(val idUsuario: Int, val nombre: String, val apellido: String, val email: String, val fechaNacimiento: String, val rol: String, val cedula: String, val activo: Boolean, val validado: Boolean)