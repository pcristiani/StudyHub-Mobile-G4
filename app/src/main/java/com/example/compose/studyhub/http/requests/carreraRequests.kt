package com.example.compose.studyhub.http.requests

import AsignaturaRequest
import CarreraRequest
import InscripcionCarreraRequest
import RetrofitClient
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun inscripcionesCarreraRequest(idUsuario: Int, token: String, callback: (List<CarreraRequest>?) -> Unit){
    val completeToken = "Bearer " + token

    RetrofitClient.api.getCarrerasInscripto(idUsuario, completeToken).enqueue(object: Callback<String>{
        override fun onResponse(call: Call<String>, response: Response<String>) {
            val responseText = response.body()

            if (response.isSuccessful) {
                val gson = Gson()
                val listType = object: TypeToken<List<CarreraRequest>>() {}.type
                val carreras: List<CarreraRequest> = gson.fromJson(responseText, listType)
                callback(carreras) // println("Response: $responseText")
            } else {
                callback(null)
                // println("Response code: ${response.code()}")
                // println("Response message: ${response.message()}")
                response.errorBody()?.let { errorBody ->
                    println("${errorBody.string()}")
                }
        }}
        override fun onFailure(call: Call<String>, t: Throwable) {
            println("Error: ${t.message}")
            callback(null)
        }
    })
}



fun getCarrerasRequest(token: String, callback: (List<CarreraRequest>?) -> Unit) { // val registerRequest = getAsignaturasAprobadas(nombre, apellido, email, fechaNacimiento, ci, password)
    val completeToken = "Bearer " + token

    RetrofitClient.api.getCarreras(completeToken).enqueue(object: Callback<String> {
        override fun onResponse(call: Call<String>, response: Response<String>) {
            val responseText = response.body()
            if (response.isSuccessful) {
                val cuttedResponseText = responseText?.substring(22, responseText.length-42)
                val jsonArrayText = """[$cuttedResponseText]"""

                val gson = GsonBuilder().setLenient().create()
                val listType = object: TypeToken<List<CarreraRequest>>() {}.type
                val carreras: List<CarreraRequest> = gson.fromJson(jsonArrayText, listType)
                callback(carreras)
            } else {
                callback(null)
                // println("Response code: ${response.code()}")
                // println("Response message: ${response.message()}")
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


fun inscripcionCarreraRequest(token: String, inscripcionCarrera: InscripcionCarreraRequest, callback: (Boolean, String?) -> Unit) {
    val completeToken = "Bearer $token"

    RetrofitClient.api.inscripcionCarrera(completeToken, inscripcionCarrera).enqueue(object: Callback<String> {
        override fun onResponse(call: Call<String>, response: Response<String>) {
            val responseText = response.body()
            if (response.isSuccessful) {
                callback(true,responseText)
            } else {
                callback(false, response.errorBody()?.string())
              //  println("Response code: ${response.code()}")
             //   println("Response message: ${response.message()}")
                response.errorBody()?.let { errorBody ->
                    println("Error body: ${errorBody.string()}"
                    )
                }
            }
        }
        override fun onFailure(call: Call<String>, t: Throwable) {
            t.printStackTrace()
            callback(false, t.message)
        }
    })
}


fun getAsignaturasDeCarreraRequest(idCarrera: Int, token: String, callback: (List<AsignaturaRequest>?) -> Unit) {
    val completeToken = "Bearer $token"

    RetrofitClient.api.getAsignaturasDeCarrera(idCarrera, completeToken).enqueue(object : Callback<String> {
        override fun onResponse(call: Call<String>, response: Response<String>) {
            val responseText = response.body()

            if (response.isSuccessful && responseText != null) {
                try {
                    val cuttedResponseText = responseText.substring(22, responseText.length - 42)
                    val jsonArrayText = """[$cuttedResponseText]"""
                    val gson = GsonBuilder().setLenient().create()
                    val listType = object : TypeToken<List<AsignaturaRequest>>() {}.type
                    val asignaturas: List<AsignaturaRequest> = gson.fromJson(jsonArrayText, listType)
                    callback(asignaturas)
                } catch (e: Exception) {
                    e.printStackTrace()
                    callback(null)
                }
            } else {
                callback(null)
             //   println("Response code: ${response.code()}")
               // println("Response message: ${response.message()}")
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
