package com.example.compose.studyhub.http.requests

import com.example.compose.studyhub.auth.SolicitudRequest
import com.example.compose.studyhub.auth.decodeAsignaturas
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun getAsignaturasAprobadasRequest(idUsuario: Int, token: String, callback: (List<SolicitudRequest>?) -> Unit){
    //val registerRequest = getAsignaturasAprobadas(nombre, apellido, email, fechaNacimiento, ci, password)

    val completeToken = "Bearer " + token

    RetrofitClient.api.getAsignaturasAprobadas(idUsuario, completeToken).enqueue(object : Callback<String> {
        override fun onResponse(call: Call<String>, response: Response<String>) {
            val responseText = response.body() // Procesar la respuesta del login



            if (responseText != null) {
                println(responseText)
            }

            if (response.isSuccessful) {

                callback(responseText?.let { decodeAsignaturas(it) })
                println(responseText)
            }
            else{
                callback(null)
                println("Response code: ${response.code()}")
                println("Response message: ${response.message()}")
                response.errorBody()?.let { errorBody ->
                    println("Error body: ${errorBody.string()}")
                }
            }
        }

        override fun onFailure(call: Call<String>, t: Throwable) {

            println("Error: ${t.message}")
            callback(null)
        }
    })

}

fun getAsignaturasNoAprobadasRequest(idUsuario: Int, token: String, callback: (List<SolicitudRequest>?) -> Unit){
    //val registerRequest = getAsignaturasAprobadas(nombre, apellido, email, fechaNacimiento, ci, password)

    val completeToken = "Bearer " + token

    RetrofitClient.api.getAsignaturasNoAprobadas(idUsuario, completeToken).enqueue(object : Callback<String> {
        override fun onResponse(call: Call<String>, response: Response<String>) {
            val responseText = response.body() // Procesar la respuesta del login



            if (responseText != null) {
                println(responseText)
            }

            if (response.isSuccessful) {

                callback(responseText?.let { decodeAsignaturas(it) })
                println(responseText)
            }
            else{
                callback(null)
                println("Response code: ${response.code()}")
                println("Response message: ${response.message()}")
                response.errorBody()?.let { errorBody ->
                    println("Error body: ${errorBody.string()}")
                }
            }
        }

        override fun onFailure(call: Call<String>, t: Throwable) {

            println("Error: ${t.message}")
            callback(null)
        }
    })

}
