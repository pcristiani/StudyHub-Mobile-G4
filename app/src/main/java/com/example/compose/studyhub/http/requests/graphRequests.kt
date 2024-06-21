package com.example.compose.studyhub.http.requests

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun getPreviaturasGrafoRequest(idCarrera:Int, token:String, callback: (String?) -> Unit){
    val completeToken = "Bearer " + token

    RetrofitClient.api.getPreviaturasGrafo(idCarrera, completeToken).enqueue(object: Callback<String> {
        override fun onResponse(call: Call<String>, response: Response<String>) {
            val responseText = response.body()

            if(responseText!=null){
                callback(responseText)
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