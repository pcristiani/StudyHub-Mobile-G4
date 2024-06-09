package com.example.compose.studyhub.http.requests

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun getAsignaturasAprobadasRequest(idUsuario: Int, token: String, callback: (Boolean) -> Unit){
    //val registerRequest = getAsignaturasAprobadas(nombre, apellido, email, fechaNacimiento, ci, password)


    RetrofitClient.api.getAsignaturasAprobadas(idUsuario, token).enqueue(object : Callback<String> {
        override fun onResponse(call: Call<String>, response: Response<String>) {
            val responseText = response.body() // Procesar la respuesta del login



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
