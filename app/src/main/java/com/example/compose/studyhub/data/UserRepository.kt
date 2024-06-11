package com.example.compose.studyhub.data

import TokenRequest
import androidx.compose.runtime.Immutable
import com.example.compose.studyhub.auth.decodeUser
import com.example.compose.studyhub.http.requests.getUsuarioRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

///
sealed class User {
   @Immutable data class LoggedInUser(val JWTtoken: String, val id: Int, val ci: String, val nombre: String, val apellido: String) : User() {
      val token: String = JWTtoken
      val ciString: String = ci
      val idUsuario: Int = id
      val nombreU: String = nombre
      val apellidoU: String = apellido
   }

   object InvitadoUser : User()
   object NoUserLoggedIn : User()
}

///
// Contiene el usuario registrado.
// Clase que manejara la comunicación con el backend // iniciar sesión y registrarse.
object UserRepository {
   private var _user: User = User.NoUserLoggedIn
   val user: User
      get() = _user


   //@Suppress("UNUSED_PARAMETER")
   fun login(token: String, id: Int, ci: String) {

      getUsuarioRequest(id, token){
         success ->
         if(success != null){
            _user = success.nombre?.let { success.apellido?.let { it1 ->
               User.LoggedInUser(token, id, ci, it,
                  it1
               )
            } }!!
         }
      }

   }

   /*
   @Suppress("UNUSED_PARAMETER")
   fun signUp(email: String, password: String) {
      _user = User.LoggedInUser(email)
   }
   */
   fun loginInvitado() {
      _user = User.InvitadoUser
   }

   fun logout() {
      _user = User.NoUserLoggedIn
   }

   fun loggedInUser(): Int?{
      return when (user){
         is User.LoggedInUser -> (user as User.LoggedInUser).idUsuario
         else -> null
      }
   }


   fun getNombre(): String?{
      return when (user){
         is User.LoggedInUser -> (user as User.LoggedInUser).nombreU
         else -> null
      }
   }

   fun getApellido(): String?{
      return when (user){
         is User.LoggedInUser -> (user as User.LoggedInUser).apellidoU
         else -> null
      }
   }


   /*
   fun existeUserCi(ci: String): Boolean {
      /*
      for (e in listEmailRegistrados) { // println(" " + e.second + e.first)
         if (email == e.second) return true
      }
       */



      RetrofitClient.api.getUsers().enqueue(object : Callback<String> {

         override fun onResponse(call: Call<String>, response: Response<String>) {
            if (response.isSuccessful) {
               val token = response.body() // Procesar la respuesta del login

               println(response)

               if (token != null) {
                  val usersList = decodeUser(token)
                  println(usersList)
               }

            } else {
               println("Incorrect credentials")
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

      return false
   }


    */

   fun getPasswordEmail(email: String): String {
      for (e in listEmailRegistrados) {
         if (email == e.second) return e.first
      }
      return "No encontrado"
   }

   fun getToken(): String? {
      return when (user){
         is User.LoggedInUser -> (user as User.LoggedInUser).token
         else -> null
      }
   }
}

///
// Usuarios registrados
val listEmailRegistrados = listOf(
   "111" to "user1@gmail.com",
   "112" to "user2@gmail.com",
   "311" to "user3@gmail.com",
   "411" to "user4@gmail.com",
   "511" to "user5@gmail.com",
   "6232" to "user6@gmail.com",
   "7231" to "grupo4.proyecto2024@gmail.com",
   "123" to "sgonzalez@gmail.com",
   "123" to "admin",
) // return !email.contains("signup") // println("UserRepository --> existeUserEmail")
