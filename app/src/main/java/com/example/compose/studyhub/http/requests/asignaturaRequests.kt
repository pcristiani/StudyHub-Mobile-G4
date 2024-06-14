package com.example.compose.studyhub.http.requests

import com.example.compose.studyhub.auth.AsignaturaRequest
import com.example.compose.studyhub.auth.SolicitudRequest
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun getAsignaturasAprobadasRequest(idUsuario: Int, token: String, callback: (List<AsignaturaRequest>?) -> Unit){
    //val registerRequest = getAsignaturasAprobadas(nombre, apellido, email, fechaNacimiento, ci, password)

    val completeToken = "Bearer " + token

    RetrofitClient.api.getAsignaturasAprobadas(idUsuario, completeToken).enqueue(object : Callback<String> {
        override fun onResponse(call: Call<String>, response: Response<String>) {
            val responseText = response.body() // Procesar la respuesta del login

            if (response.isSuccessful) {



                val gson = Gson()
                val listType = object : TypeToken<List<AsignaturaRequest>>() {}.type
                val asignaturas: List<AsignaturaRequest> = gson.fromJson(responseText, listType)
                callback(asignaturas)
                //println("Response: $responseText")
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

fun getAsignaturasNoAprobadasRequest(idUsuario: Int, token: String, callback: (List<AsignaturaRequest>?) -> Unit){
    //val registerRequest = getAsignaturasAprobadas(nombre, apellido, email, fechaNacimiento, ci, password)

    val completeToken = "Bearer " + token

    RetrofitClient.api.getAsignaturasNoAprobadas(idUsuario, completeToken).enqueue(object : Callback<String> {
        override fun onResponse(call: Call<String>, response: Response<String>) {
            val responseText = response.body() // Procesar la respuesta del login

            if (response.isSuccessful) {



                val gson = Gson()
                val listType = object : TypeToken<List<AsignaturaRequest>>() {}.type
                val asignaturas: List<AsignaturaRequest> = gson.fromJson(responseText, listType)
                callback(asignaturas)
                //println("Response: $responseText")
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
