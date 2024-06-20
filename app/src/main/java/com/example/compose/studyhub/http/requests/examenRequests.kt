package com.example.compose.studyhub.http.requests

import AsignaturaRequest
import CalificacionExamenRequest
import com.example.compose.studyhub.auth.SolicitudRequest
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun getCalificacionesExamenesRequest(idUsuario: Int, idCarrera: Int, token: String, callback: (List<CalificacionExamenRequest>?) -> Unit) {
    val completeToken = "Bearer " + token


    RetrofitClient.api.getCalificacionesExamenes(idUsuario, idCarrera, completeToken).enqueue(object: Callback<String>{
        override fun onResponse(call: Call<String>, response: Response<String>) {
            val responseText = response.body()

            if (response.isSuccessful) {
                val gson = Gson()
                val listType = object: TypeToken<List<CalificacionExamenRequest>>() {}.type
                val calificacionesExamen: List<CalificacionExamenRequest> = gson.fromJson(responseText, listType)
                callback(calificacionesExamen)
            } else {
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