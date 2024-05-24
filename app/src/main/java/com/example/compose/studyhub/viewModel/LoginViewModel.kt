package com.example.compose.studyhub.viewModel

import LoginRequest
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.compose.studyhub.auth.decodeJWT
import com.example.compose.studyhub.data.UserRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

///
class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {
   fun login(ci: String, password: String, onLoginComplete: () -> Unit) {


      /*userRepository.login(email, password)
      val passwordState = userRepository.getPasswordEmail(email)
      if (passwordState == password) {
         println("Login correcto")
         onLoginComplete()
      } else {
         println("Password incorrecta ")
      }*/


      val loginRequest = LoginRequest(ci, password)



      RetrofitClient.api.login(loginRequest).enqueue(object : Callback<String> {
         override fun onResponse(call: Call<String>, response: Response<String>) {
            if (response.isSuccessful) {
               val token = response.body() // Procesar la respuesta del login

               println("I'm here");

               if (token != null) {
                  val decodedResponse = decodeJWT(token)
               }

               onLoginComplete()
            } else {
               println("Incorrect credentials")
            }
         }

         override fun onFailure(call: Call<String>, t: Throwable) {

            println("Error: ${t.message}")
         }
      })
   }

   fun loginInvitado(onLoginComplete: () -> Unit) {
      userRepository.loginInvitado()
      onLoginComplete()
   }
}

///
class LoginViewModelFactory : ViewModelProvider.Factory {
   @Suppress("UNCHECKED_CAST")
   override fun <T : ViewModel> create(modelClass: Class<T>): T {
      if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
         return LoginViewModel(UserRepository) as T
      }
      throw IllegalArgumentException("Unknown ViewModel class")
   }
}
