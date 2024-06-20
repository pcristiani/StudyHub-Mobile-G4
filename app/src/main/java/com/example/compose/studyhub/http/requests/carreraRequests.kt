package com.example.compose.studyhub.http.requests

import AsignaturaRequest
import CarreraRequest
import com.google.gson.Gson
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
                println("Response code: ${response.code()}")
                println("Response message: ${response.message()}")
                response.errorBody()?.let { errorBody ->
                    println("Error body: ${errorBody.string()}")
                }
        }}
        override fun onFailure(call: Call<String>, t: Throwable) {
            println("Error: ${t.message}")
            callback(null)
        }
    })
}






fun getCarrerasRequest(token: String, callback: (List<CarreraRequest>?) -> Unit) { // val registerRequest = getAsignaturasAprobadas(nombre, apellido, email, fechaNacimiento, ci, password)
    val completeTokens = "Bearer " + token

    RetrofitClient.api.getCarreras(completeTokens).enqueue(object: Callback<String> {
        override fun onResponse(call: Call<String>, response: Response<String>) {
            val responseText = response.body() // Procesar la respuesta del login

            if (response.isSuccessful) {
                val gson = Gson()
                val listType = object: TypeToken<List<CarreraRequest>>() {}.type
                val carreras: List<CarreraRequest> = gson.fromJson(responseText, listType)
                callback(carreras) // println("Response: $responseText")
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




object CarreraRepository {
    fun getCarreras(jwtLogin: String, onResult: (String?) -> Unit) {
        val call = RetrofitClient.api.getCarreras("Bearer $jwtLogin")

        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    println("Responses: ${response.body()}")
                    onResult(response.body())
                } else {
                    onResult(null)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                println("Error: ${t.message}")
                onResult(null)
            }
        })
    }
}