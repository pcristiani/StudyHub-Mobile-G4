package com.example.compose.studyhub.http.requests

import com.example.compose.studyhub.data.UserRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun registerTokenRequest(idUsuario: Int, token: String) {
   val tokenRequest = "Bearer " + UserRepository.getToken()
   
   
   
   if (tokenRequest != null) {
      RetrofitClient.api.registerMobileToken(idUsuario, token, tokenRequest).enqueue(object: Callback<String> {
         override fun onResponse(call: Call<String>, response: Response<String>) {
            if (response.isSuccessful) {
               println("Token registered successfully")
            } else {
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
}