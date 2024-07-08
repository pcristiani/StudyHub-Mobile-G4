package com.example.compose.studyhub.viewModel

import InscripcionAsignaturaRequest
import InscripcionCarreraRequest
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.compose.studyhub.data.UserRepository
import com.example.compose.studyhub.http.requests.inscripcionAsignaturaRequest
import com.example.compose.studyhub.http.requests.inscripcionCarreraRequest
import com.example.compose.studyhub.http.requests.modifyProfileRequest
import com.example.compose.studyhub.http.requests.registerRequest
import kotlinx.coroutines.launch

class InscripcionAsignaturaViewModel(private val userRepository: UserRepository): ViewModel() {
  private val _inscripcionAsignaturaError = MutableLiveData<String?>(null)
  val inscripcionAsignaturaError: LiveData<String?> get() = _inscripcionAsignaturaError


  // Considere todos los registros exitosos
  fun inscripcionAsignatura(idAsignatura: Int, idHorario: Int, onInscripcionAsignaturaSubmitted: (String) -> Unit, onInscripcionAsignaturaError: (String)->Unit) {
    UserRepository.getToken()?.let { token ->
      UserRepository.loggedInUser()?.let { idUsuario ->

        inscripcionAsignaturaRequest(token, InscripcionAsignaturaRequest(idUsuario, idAsignatura, idHorario)) { success, response ->
          if (success) {
            onInscripcionAsignaturaSubmitted(response?:"")
          } else {
            onInscripcionAsignaturaError(response?:"")
          }
        }
      }
    }

  }

  fun clearError() {
    _inscripcionAsignaturaError.value = null
  }

}
class InscripcionAsignaturaViewModelFactory: ViewModelProvider.Factory {
  @Suppress("UNCHECKED_CAST")
  override fun <T: ViewModel> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(InscripcionAsignaturaViewModel::class.java)) {
      return InscripcionAsignaturaViewModel(UserRepository) as T
    }
    throw IllegalArgumentException("Unknown ViewModel class")
  }
}
