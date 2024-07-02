package com.example.compose.studyhub.http.requests

import AsignaturaRequest
import CalificacionExamenRequest
import ExamenRequest
import InscripcionExamenRequest
import RetrofitClient
import com.example.compose.studyhub.http.auth.SolicitudRequest
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun getCalificacionesExamenesRequest(idUsuario: Int, idCarrera: Int, token: String, callback: (List<CalificacionExamenRequest>?) -> Unit) {
    val completeToken = "Bearer $token"

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

fun getExamenesAsignatura(idAsignatura: Int, token: String, callback: (List<ExamenRequest>?) -> Unit){
    val completeToken = "Bearer $token"

    RetrofitClient.api.getExamenesAsignatura(idAsignatura, completeToken).enqueue(object: Callback<String>{
        override fun onResponse(call: Call<String>, response: Response<String>) {
            val responseText = response.body()

            if (response.isSuccessful) {
                val gson = Gson()
                val listType = object: TypeToken<List<ExamenRequest>>() {}.type
                val examenes: List<ExamenRequest> = gson.fromJson(responseText, listType)
                callback(examenes)
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


fun inscripcionExamenRequest(token: String, inscripcionExamenRequest: InscripcionExamenRequest, callback: (Boolean, String?) -> Unit) {
    val completeToken = "Bearer $token"

    RetrofitClient.api.inscripcionExamen(completeToken, inscripcionExamenRequest).enqueue(object : Callback<String> {
        override fun onResponse(call: Call<String>, response: Response<String>) {
            val responseText = response.body()

            if (response.isSuccessful) {
                callback(true, responseText)
            } else {
                val errorMessage = buildString {
                    append("Response code: ${response.code()}\n")
                    append("Response message: ${response.message()}\n")
                    response.errorBody()?.let { errorBody ->
                        append("Error body: ${errorBody.string()}")
                    }
                }
                callback(false, errorMessage)
            }
        }

        override fun onFailure(call: Call<String>, t: Throwable) {
            val errorMessage = "Error: ${t.message}"
            callback(false, errorMessage)
        }
    })
}
