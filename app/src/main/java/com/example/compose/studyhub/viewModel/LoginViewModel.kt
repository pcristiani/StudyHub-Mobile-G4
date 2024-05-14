package com.example.compose.studyhub.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.compose.studyhub.data.UserRepository

///
class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {
   fun login(email: String, password: String, onLoginComplete: () -> Unit) {
      userRepository.login(email, password)
      val passwordState = userRepository.getPasswordEmail(email)
      if (passwordState == password) {
         println("Login correcto")
         onLoginComplete()
      } else {
         println("Password incorrecta ")
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
