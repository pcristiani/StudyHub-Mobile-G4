package com.example.compose.studyhub.viewModel

import InscripcionCarreraRequest
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.compose.studyhub.data.UserRepository
import com.example.compose.studyhub.http.requests.inscripcionCarreraRequest
import com.example.compose.studyhub.http.requests.modifyProfileRequest
import com.example.compose.studyhub.http.requests.registerRequest
import kotlinx.coroutines.launch

class InscripcionCarreraViewModel(private val userRepository: UserRepository): ViewModel() {
  private val _inscripcionCarreraError = MutableLiveData<String?>(null)
  val inscripcionCarreraError: LiveData<String?> get() = _inscripcionCarreraError


  // Considere todos los registros exitosos
  fun inscripcionCarrera(idCarrera: Int, onInscripcionCarreraSubmitted: () -> Unit) {
    val modifyProfileRequest = UserRepository.getToken()?.let { token ->
      UserRepository.loggedInUser()?.let { idUsuario ->


        inscripcionCarreraRequest(
          token,
          InscripcionCarreraRequest(idCarrera, idUsuario, true)
        ) { success, response ->
          if (success) {
            onInscripcionCarreraSubmitted()

          } else {
            _inscripcionCarreraError.value = response

          }
        }
      }
    }

  }

  fun clearError() {
    _inscripcionCarreraError.value = null
  }

}
class InscripcionCarreraViewModelFactory: ViewModelProvider.Factory {
  @Suppress("UNCHECKED_CAST")
  override fun <T: ViewModel> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(InscripcionCarreraViewModel::class.java)) {
      return InscripcionCarreraViewModel(UserRepository) as T
    }
    throw IllegalArgumentException("Unknown ViewModel class")
  }
}
