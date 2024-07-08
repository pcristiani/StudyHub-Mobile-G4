package com.example.compose.studyhub.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.compose.studyhub.data.UserRepository
import com.example.compose.studyhub.http.requests.modifyProfileRequest
import com.example.compose.studyhub.http.requests.registerRequest

class EditarPerfilViewModel(private val userRepository: UserRepository): ViewModel() {
  // Considere todos los registros exitosos
  fun modifyProfile(nombre: String, apellido: String, email: String, fechaNacimiento: String, onModifyProfileSubmitted: () -> Unit) {
    val modifyProfileRequest = UserRepository.getToken()?.let { token ->
      UserRepository.loggedInUser()?.let { idUsuario ->
        modifyProfileRequest(token, idUsuario, nombre, apellido, email, fechaNacimiento) { success ->
          if (success) {
            onModifyProfileSubmitted()
            UserRepository.modificarPerfil()
          }
        }
      }
    }
  }
}

class EditarPerfilViewModelFactory: ViewModelProvider.Factory {
  @Suppress("UNCHECKED_CAST")
  override fun <T: ViewModel> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(EditarPerfilViewModel::class.java)) {
      return EditarPerfilViewModel(UserRepository) as T
    }
    throw IllegalArgumentException("Unknown ViewModel class")
  }
}
