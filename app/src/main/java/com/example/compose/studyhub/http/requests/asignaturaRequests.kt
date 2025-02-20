package com.example.compose.studyhub.http.requests

import AsignaturaRequest
import CalificacionAsignaturaRequest
import HorariosAsignaturaRequest
import InscripcionAsignaturaRequest
import RetrofitClient
import com.example.compose.studyhub.http.auth.SolicitudRequest
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun getAsignaturasAprobadasRequest(idUsuario: Int, token: String, callback: (List<AsignaturaRequest>?) -> Unit) { // val registerRequest = getAsignaturasAprobadas(nombre, apellido, email, fechaNacimiento, ci, password)
   val completeToken = "Bearer $token"
   
   RetrofitClient.api.getAsignaturasAprobadas(idUsuario, completeToken).enqueue(object: Callback<String> {
      override fun onResponse(call: Call<String>, response: Response<String>) {
         val responseText = response.body() // Procesar la respuesta del login
         
         if (response.isSuccessful) {
            val gson = Gson()
            val listType = object: TypeToken<List<AsignaturaRequest>>() {}.type
            val asignaturas: List<AsignaturaRequest> = gson.fromJson(responseText, listType)
            callback(asignaturas)
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

fun getAsignaturasNoAprobadasRequest(idUsuario: Int, token: String, callback: (List<AsignaturaRequest>?) -> Unit) { // val registerRequest = getAsignaturasAprobadas(nombre, apellido, email, fechaNacimiento, ci, password)
   val completeToken = "Bearer $token"
   
   RetrofitClient.api.getAsignaturasNoAprobadas(idUsuario, completeToken).enqueue(object: Callback<String> {
      override fun onResponse(call: Call<String>, response: Response<String>) {
         val responseText = response.body() // Procesar la respuesta del login
         
         if (response.isSuccessful) {
            val gson = Gson()
            val listType = object: TypeToken<List<AsignaturaRequest>>() {}.type
            val asignaturas: List<AsignaturaRequest> = gson.fromJson(responseText, listType)
            callback(asignaturas)
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

fun getCalificacionesAsignaturasRequest(idUsuario: Int, idCarrera: Int, token: String, callback: (List<CalificacionAsignaturaRequest>?) -> Unit) {
   val completeToken = "Bearer $token"

   RetrofitClient.api.getCalificacionesAsignatura(idUsuario, idCarrera, completeToken).enqueue(object: Callback<String>{
      override fun onResponse(call: Call<String>, response: Response<String>) {
         val responseText = response.body()

         if (response.isSuccessful) {
            val gson = Gson()
            val listType = object: TypeToken<List<CalificacionAsignaturaRequest>>() {}.type
            val calificacionesAsignatura: List<CalificacionAsignaturaRequest> = gson.fromJson(responseText, listType)
            callback(calificacionesAsignatura)
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

fun getHorariosAsignaturaRequest(idAsignatura:Int, token: String, callback: (List<HorariosAsignaturaRequest>?) -> Unit){
   val completeToken = "Bearer $token"

   RetrofitClient.api.getHorariosAsignatura(idAsignatura, completeToken).enqueue(object: Callback<String>{
      override fun onResponse(call: Call<String>, response: Response<String>) {
         val responseText = response.body()

         if (response.isSuccessful) {
            val gson = Gson()
            val listType = object: TypeToken<List<HorariosAsignaturaRequest>>() {}.type
            val horariosAsignatura: List<HorariosAsignaturaRequest> = gson.fromJson(responseText, listType)
            callback(horariosAsignatura)
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

fun inscripcionAsignaturaRequest(
   token: String,
   inscripcionAsignaturaRequest: InscripcionAsignaturaRequest,
   callback: (Boolean, String?) -> Unit
) {
   val completeToken = "Bearer $token"

   RetrofitClient.api.inscripcionAsignatura(completeToken, inscripcionAsignaturaRequest).enqueue(object : Callback<String> {
      override fun onResponse(call: Call<String>, response: Response<String>) {
         val responseText = response.body()
         if (response.isSuccessful) {
            callback(true, responseText)
         } else {
            val errorMessage = buildString {
               response.errorBody()?.let { errorBody ->
                  append(errorBody.string())
               }
            }
            callback(false, errorMessage)
         }
      }
       override fun onFailure(call: Call<String>, t: Throwable) {
           var retryCount: Int = 3

           if (retryCount > 0) {
               // Retrying the request
               retryCount--
               inscripcionAsignaturaRequest(token, inscripcionAsignaturaRequest, callback)
           } else {
               val errorMessage = "Error: ${t.message ?: "unknown error"}"
               callback(false, errorMessage)
           }
       }
   /*   override fun onFailure(call: Call<String>, t: Throwable) {
        // val errorMessage = "Error: ${t.message}"
          val errorMessage = "Error: ${t.message} (cause: ${t.cause})"
         callback(false, errorMessage)
      }*/
   })
}


fun getAsignaturasConExamenPendienteRequest(idUsuario:Int, idCarrera:Int, token:String, callback: (List<AsignaturaRequest>?) -> Unit){
   val completeToken = "Bearer $token"

   RetrofitClient.api.getAsignaturasConExamenPendiente(idUsuario,idCarrera,completeToken).enqueue(object: Callback<String>{
      override fun onResponse(call: Call<String>, response: Response<String>) {
         val responseText = response.body() // Procesar la respuesta del login

         if (response.isSuccessful) {
            val gson = Gson()
            val listType = object: TypeToken<List<AsignaturaRequest>>() {}.type
            val asignaturas: List<AsignaturaRequest> = gson.fromJson(responseText, listType)
            callback(asignaturas)
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