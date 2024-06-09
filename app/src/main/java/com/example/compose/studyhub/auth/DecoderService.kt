package com.example.compose.studyhub.auth

import com.auth0.android.jwt.JWT
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun decodeJWT(token: String): UserRequest? {
    try {
        val jwt = JWT(token)


        // Acceder a los claims
        val idUsuario = jwt.getClaim("id").asInt()
        val nombre = jwt.getClaim("nombre").asString()
        val apellido = jwt.getClaim("apellido").asString()
        val email = jwt.getClaim("email").asString()
        val fechaNacimiento = jwt.getClaim("fechaNacimiento").asString()
        val cedula = jwt.getClaim("cedula").asString()
        val rol = jwt.getClaim("rol").asString()
        val activo = jwt.getClaim("activo").asBoolean()
        val validado = jwt.getClaim("validado").asBoolean()

        println("ID: $idUsuario")
        println("CI: $cedula")
        println("Rol: $rol")




        return UserRequest(
            idUsuario,
            nombre,
            apellido,
            email,
            fechaNacimiento,
            cedula,
            rol,
            activo,
            validado
        )

    } catch (e: Exception) {
        e.printStackTrace()
        return null
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

fun decodeAsignaturas(token: String): List<SolicitudRequest>?{
    return try {

        val jwt = JWT(token)

        val asignaturaJson = jwt.getClaim("").asString() ?: throw IllegalArgumentException("Users claim is missing or invalid")


        val gson = Gson()
        val asignaturaType = object : TypeToken<List<AsignaturaRequest>>() {}.type
        println(asignaturaType)

        gson.fromJson<List<SolicitudRequest>>(asignaturaJson, asignaturaType)


    }catch (e: Exception) {
        e.printStackTrace()
        null
    } catch (e: IllegalArgumentException) {
        e.printStackTrace()
        null
    }


}

data class AsignaturaRequest(val idAsignatura: Int, val idCarrera: Int, val nombre: String, val creditos: Int, val descripcion: String, val departamento: String, val tieneExamen: Boolean, val activa: Boolean, val previaturas: List<Int>)

data class SolicitudRequest(val idCarrera: Int, val nombre: String, val descripcion: String, val requisitos: String, val duracion: Int)

data class UserRequest(val idUsuario: Int?, val nombre: String?, val apellido: String?, val email: String?, val fechaNacimiento: String?, val rol: String?, val cedula: String?, val activo: Boolean?, val validado: Boolean?)