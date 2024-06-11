package com.example.compose.studyhub.auth

import com.auth0.android.jwt.JWT
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

<<<<<<< HEAD
fun decodeJWT(token: String): LoginResponse? {
    try {
        val jwt = JWT(token)


        // Acceder a los claims
        val idUsuario = jwt.getClaim("id").asInt()
        val cedula = jwt.getClaim("cedula").asString()
        val rol = jwt.getClaim("rol").asString()

        println("ID: $idUsuario")
        println("CI: $cedula")
        println("Rol: $rol")




        return LoginResponse(
            idUsuario,
            cedula,
            rol
        )

    } catch (e: Exception) {
        e.printStackTrace()
        return null
    }
}


fun decodeUser(token: String): List<LoginResponse>? {
    return try {
        val jwt = JWT(token)

        // Acceder a los claims
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
=======
fun decodeJWT(token: String): UserRequest? {
   try {
      val jwt = JWT(token)
      println("jwt: $jwt")
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
         idUsuario, nombre, apellido, email, fechaNacimiento, rol, cedula, activo, validado
                        )
      
   } catch (e: Exception) {
      e.printStackTrace()
      return null
   }
}

fun decodeUser(token: String): List<UserRequest>? {
   return try {
      val jwt = JWT(token) // Acceder a los claims
      val usersJson = jwt.getClaim("users").asString() ?: throw IllegalArgumentException("Users claim is missing or invalid")
      val gson = Gson()
      val userType = object: TypeToken<List<UserRequest>>() {}.type
      gson.fromJson<List<UserRequest>>(usersJson, userType)
      
   } catch (e: Exception) {
      e.printStackTrace()
      null
   } catch (e: IllegalArgumentException) {
      e.printStackTrace()
      null
   }
>>>>>>> 761489537ddbfc4ff151a1e44adb959a6ec25c31
}

fun decodeSolicitudes(token: String): List<SolicitudRequest>? {
   return try {
      val jwt = JWT(token)
      val usersJson = jwt.getClaim("").asString() ?: throw IllegalArgumentException("Users claim is missing or invalid")
      val gson = Gson()
      val userType = object: TypeToken<List<SolicitudRequest>>() {}.type
      gson.fromJson<List<SolicitudRequest>>(usersJson, userType)
      
   } catch (e: Exception) {
      e.printStackTrace()
      null
   } catch (e: IllegalArgumentException) {
      e.printStackTrace()
      null
   }
   
}

fun decodeAsignaturas(token: String): List<SolicitudRequest>? {
   return try {
      val jwt = JWT(token)
      val asignaturaJson = jwt.getClaim("").asString() ?: throw IllegalArgumentException("Users claim is missing or invalid")
      val gson = Gson()
      val asignaturaType = object: TypeToken<List<AsignaturaRequest>>() {}.type
      println(asignaturaType)
      
      gson.fromJson<List<SolicitudRequest>>(asignaturaJson, asignaturaType)
      
   } catch (e: Exception) {
      e.printStackTrace()
      null
   } catch (e: IllegalArgumentException) {
      e.printStackTrace()
      null
   }
   
}

data class AsignaturaRequest(val idAsignatura: Int, val idCarrera: Int, val nombre: String, val creditos: Int, val descripcion: String, val departamento: String, val tieneExamen: Boolean, val activa: Boolean, val previaturas: List<Int>)

data class SolicitudRequest(val idCarrera: Int, val nombre: String, val descripcion: String, val requisitos: String, val duracion: Int)

data class LoginResponse(val idUsuario: Int?, val cedula: String?, val rol: String?)