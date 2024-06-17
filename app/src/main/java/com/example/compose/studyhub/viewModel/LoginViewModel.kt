package com.example.compose.studyhub.viewModel

import LoginRequest
import android.widget.Toast
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.compose.studyhub.R
import com.example.compose.studyhub.auth.decodeJWT
import com.example.compose.studyhub.data.User
import com.example.compose.studyhub.data.UserRepository
import com.example.compose.studyhub.http.requests.loginRequest
import com.example.compose.studyhub.services.PushNotificationService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

///
class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {
   private val _loginError = MutableLiveData<String?>(null)
   val loginError: LiveData<String?> get() = _loginError

   var successfulLogin: Boolean = false

   fun login(ci: String, password: String, onLoginComplete: () -> Unit) {


      /*userRepository.login(email, password)
      val passwordState = userRepository.getPasswordEmail(email)
      if (passwordState == password) {
         println("Login correcto")
         onLoginComplete()k
      } else {
         println("Password incorrecta ")
      }*/





      loginRequest(ci, password) {success ->
         if(success){
            onLoginComplete()
            successfulLogin = true
         }
      }


      if(successfulLogin == false){
         _loginError.value = "Credenciales incorrectas."
         println("Credenciales incorrectas")
      }
      successfulLogin = false
   }


   fun clearLoginError(){
      _loginError.value = null
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
