package com.example.compose.studyhub.http.requests

import LoginRequest
import ModifyProfileRequest
import RegisterRequest
import UserRequest
import RetrofitClient
import com.example.compose.studyhub.http.auth.decodeJWT
import com.example.compose.studyhub.data.UserRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun loginRequest(ci: String, password: String, callback: (Boolean, String) -> Unit) {
   val loginRequest = LoginRequest(ci, password)
   
   
   
   RetrofitClient.api.login(loginRequest).enqueue(object: Callback<String> {
      override fun onResponse(call: Call<String>, response: Response<String>) {
         if (response.isSuccessful) {
            val token = response.body() // Procesar la respuesta del login
            
            
            
            if (token != null) {
               val decodedResponse = decodeJWT(token)
               
               if (decodedResponse != null) {
                  decodedResponse.idUsuario?.let { idUsuario ->
                     decodedResponse.cedula?.let { ci ->
                        if (decodedResponse.rol == "E") {
                           UserRepository.login(token, idUsuario, ci)
                           callback(true, "")
                        } else {
                           println("ACA ESTOY" + decodedResponse.rol)
                           
                           callback(false, token)
                        }
                     }
                  }
               }
               
            } else {
               callback(false, "")
            }
         }
      }
      
      override fun onFailure(call: Call<String>, t: Throwable) {
         println("Error: ${t.message}")
         callback(false, t.message?:"")
      }
   })
   
}

fun cerrarSesionRequest(token: String, callback: (Boolean) -> Unit) { // val completeToken = "Bearer $token"
   RetrofitClient.api.logout(token).enqueue(object: Callback<String> {
      override fun onResponse(call: Call<String>, response: Response<String>) {
         val responseText = response.body() // Procesar la respuesta del login
         
         if (response.isSuccessful) {
            callback(true)
            println(responseText)
         } else {
            callback(false)
            println("Response code: ${response.code()}")
            println("Response message: ${response.message()}")
            response.errorBody()?.let { errorBody ->
               println("Error body: ${errorBody.string()}")
            }
         }
      }
      
      override fun onFailure(call: Call<String>, t: Throwable) {
         println("Error: ${t.message}")
      }
      
   })
}

fun registerRequest(nombre: String, apellido: String, email: String, fechaNacimiento: String, ci: String, password: String, callback: (Boolean) -> Unit) {
   val registerRequest = RegisterRequest(nombre, apellido, email, fechaNacimiento, ci, password)
   
   println(registerRequest)
   
   
   RetrofitClient.api.signUp(registerRequest).enqueue(object: Callback<String> {
      override fun onResponse(call: Call<String>, response: Response<String>) {
         val responseText = response.body() // Procesar la respuesta del login
         
         
         
         if (responseText != null) {
            println(responseText)
         }
         
         if (response.isSuccessful) {
            callback(true)
            println(responseText)
         } else {
            callback(false)
         }
      }
      
      override fun onFailure(call: Call<String>, t: Throwable) {
         println("Error: ${t.message}")
         callback(false)
      }
   })
   
}

fun getUsuarioRequest(idUsuario: Int, token: String, callback: (UserRequest?) -> Unit) {
   val completeToken = "Bearer $token"
   val call = RetrofitClient.api.getUsuario(idUsuario, completeToken)
   call.enqueue(object: Callback<UserRequest> {
      override fun onResponse(call: Call<UserRequest>, response: Response<UserRequest>) {
         if (response.isSuccessful) {
            callback(response.body())
         } else {
            callback(null)
            println("Response code: ${response.code()}")
            println("Response message: ${response.message()}")
            response.errorBody()?.let { errorBody ->
               println("Error body: ${errorBody.string()}")
            }
         }
      }
      
      override fun onFailure(call: Call<UserRequest>, t: Throwable) {
         t.printStackTrace()
         callback(null)
      }
      
   })
}

fun modifyProfileRequest(token: String, idUsuario: Int, nombre: String, apellido: String, email: String, fechaNacimiento: String, callback: (Boolean) -> Unit) {
   val fullToken = "Bearer $token"
   val modifyProfileRequest = ModifyProfileRequest(nombre, apellido, email, fechaNacimiento)
   
   
   
   RetrofitClient.api.modifyProfile(idUsuario, fullToken, modifyProfileRequest).enqueue(object: Callback<String> {
      override fun onResponse(call: Call<String>, response: Response<String>) {
         val responseText = response.body() // Procesar la respuesta del login
         
         
         
         if (responseText != null) {
            println(responseText)
         }
         
         if (response.isSuccessful) {
            callback(true)
            println(responseText)
         } else {
            callback(false)
            println("Response code: ${response.code()}")
            println("Response message: ${response.message()}")
            response.errorBody()?.let { errorBody ->
               println("Error body: ${errorBody.string()}")
            }
         }
      }
      
      override fun onFailure(call: Call<String>, t: Throwable) {
         println("Error: ${t.message}")
         callback(false)
      }
   })
   
}

fun forgotPasswordRequest(email: String, callback: (state: Boolean, response: String) -> Unit){

   println("Email: $email")
   RetrofitClient.api.forgotPassword(email).enqueue(object: Callback<String> {
      override fun onResponse(call: Call<String>, response: Response<String>) {
         val responseText = response.body() // Procesar la respuesta del login

         if (response.isSuccessful) {
            if (responseText != null) {
               callback(true, responseText)
            }
            println(responseText)
         } else {
            if (responseText != null) {
               callback(false, responseText)
            }
            println("Response code: ${response.code()}")
            println("Response message: ${response.message()}")
            response.errorBody()?.let { errorBody ->
               println("Error body: ${errorBody.string()}")
            }
         }
      }

      override fun onFailure(call: Call<String>, t: Throwable) {
         println("Error: ${t.message}")
         t.message?.let { callback(false, it) }
      }
   })
}
