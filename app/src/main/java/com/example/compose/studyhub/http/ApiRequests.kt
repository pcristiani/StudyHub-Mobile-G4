package com.example.compose.studyhub.http

import LoginRequest
import RegisterRequest
import RetrofitClient
import TokenRequest // import TokenRequest
import com.example.compose.studyhub.auth.SolicitudRequest
import com.example.compose.studyhub.auth.decodeJWT
import com.example.compose.studyhub.auth.decodeSolicitudes
import com.example.compose.studyhub.data.User
import com.example.compose.studyhub.data.UserRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun solicitudesRequest(callback: (List<SolicitudRequest>?) -> Unit) {
   RetrofitClient.api.getInscPendientes().enqueue(object: Callback<String> {
      override fun onResponse(call: Call<String>, response: Response<String>) {
         val token = response.body()
         
         println(response)
         if (token != null) {
            println(token)
         }
         
         if (response.isSuccessful) {
            val decodedResponse = token?.let { decodeSolicitudes(it) }
            
            println("Response code: ${response.code()}")
            println("Response message: ${response.message()}")
            response.errorBody()?.let { errorBody ->
               println("Error body: ${errorBody.string()}")
            }
            callback(decodedResponse)
         }
         
      }
      
      override fun onFailure(call: Call<String>, t: Throwable) {
         println("Error: ${t.message}")
         
      }
      
   })
   
}




