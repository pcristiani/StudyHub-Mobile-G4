package com.example.compose.studyhub.http

import LoginRequest
import RegisterRequest
import RetrofitClient
import TokenRequest
import com.example.compose.studyhub.auth.SolicitudRequest
import com.example.compose.studyhub.auth.decodeJWT
import com.example.compose.studyhub.auth.decodeSolicitudes
import com.example.compose.studyhub.data.User
import com.example.compose.studyhub.data.UserRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun loginRequest(ci: String, password: String, callback: (Boolean) -> Unit){

    val loginRequest = LoginRequest(ci, password)



    RetrofitClient.api.login(loginRequest).enqueue(object : Callback<String> {
        override fun onResponse(call: Call<String>, response: Response<String>) {
            if (response.isSuccessful) {
                val token = response.body() // Procesar la respuesta del login



                if (token != null) {
                    val decodedResponse = decodeJWT(token)

                    if(decodedResponse != null){
                        decodedResponse.idUsuario?.let { idUsuario ->
                            decodedResponse.cedula?.let { ci ->
                                UserRepository.login(idUsuario, ci) }
                            println("I'm here")
                        }
                    }
                    callback(true)

                }
                else{
                    callback(false)
                }
            }
        }
        override fun onFailure(call: Call<String>, t: Throwable) {

            println("Error: ${t.message}")
            callback(false)
        }
    })

}



fun registerRequest(nombre: String, apellido: String, email: String, fechaNacimiento: String, ci:String, password:String, callback: (Boolean) -> Unit){
    val registerRequest = RegisterRequest(nombre, apellido, email, fechaNacimiento, ci, password)

    println(registerRequest)

    RetrofitClient.api.signUp(registerRequest).enqueue(object : Callback<String> {
        override fun onResponse(call: Call<String>, response: Response<String>) {
            val responseText = response.body() // Procesar la respuesta del login

            println(response)

            if (responseText != null) {
                println(responseText)
            }

            if (response.isSuccessful) {

                callback(true)
                println(responseText)
            }
            else{
                callback(false)
            }
        }

        override fun onFailure(call: Call<String>, t: Throwable) {

            println("Error: ${t.message}")
            callback(false)
        }
    })

}

fun solicitudesRequest(callback: (List<SolicitudRequest>?) -> Unit){

    RetrofitClient.api.getInscPendientes().enqueue(object : Callback<String>{
        override fun onResponse(call: Call<String>, response: Response<String>) {
            val token = response.body()

            println(response)
            if (token != null) {
                println(token)
            }

            if(response.isSuccessful){
                val decodedResponse = token?.let { decodeSolicitudes(it) }

                callback (decodedResponse)
            }

        }

        override fun onFailure(call: Call<String>, t: Throwable) {
            println("Error: ${t.message}")
        }

    })

}

fun registerTokenRequest(idUsuario: Int, token: String){
    val tokenRequest = TokenRequest(token)
    RetrofitClient.api.registerMobileToken(idUsuario, tokenRequest).enqueue(object : Callback<String>{
        override fun onResponse(call: Call<String>, response: Response<String>) {


            if(response.isSuccessful){
                println("Token registered successfully")
            }else{
                println("Failed to register token for user: $idUsuario")
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