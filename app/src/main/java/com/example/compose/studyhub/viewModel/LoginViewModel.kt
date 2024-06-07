package com.example.compose.studyhub.viewModel

import LoginRequest
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.compose.studyhub.auth.decodeJWT
import com.example.compose.studyhub.data.User
import com.example.compose.studyhub.data.UserRepository
import com.example.compose.studyhub.http.loginRequest
import com.example.compose.studyhub.services.PushNotificationService
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

      val bool: Boolean

      val resp = loginRequest(ci, password) {success ->
         if(success){
            println("Wenas")
            onLoginComplete()
         }
         else{
            println("Incorrect credentials")
         }

      }


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
