package com.example.compose.studyhub.viewModel

import RegisterRequest
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.compose.studyhub.auth.decodeJWT
import com.example.compose.studyhub.data.UserRepository
import com.example.compose.studyhub.http.requests.registerRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

///
class RegisterViewModel(private val userRepository: UserRepository): ViewModel() {
  // Considere todos los registros exitosos
  fun signUp(nombre: String, apellido: String, email: String, fechaNacimiento: String, ci: String, password: String, onRegisterSubmitted: () -> Unit) {
    registerRequest(nombre, apellido, email, fechaNacimiento, ci, password) { success ->
      if (success) {
        onRegisterSubmitted()
      }
    }
  }

  fun loginInvitado(onRegisterSubmitted: () -> Unit) {
    userRepository.loginInvitado()
    onRegisterSubmitted()
  }
}
///
class RegisterViewModelFactory: ViewModelProvider.Factory {
  @Suppress("UNCHECKED_CAST")
  override fun <T: ViewModel> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
      return RegisterViewModel(UserRepository) as T
    }
    throw IllegalArgumentException("Unknown ViewModel class")
  }
}

}