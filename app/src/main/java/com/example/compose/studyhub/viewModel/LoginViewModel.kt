package com.example.compose.studyhub.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.compose.studyhub.data.UserRepository
import com.example.compose.studyhub.http.requests.loginRequest

class LoginViewModel(private val userRepository: UserRepository): ViewModel() {
  private val _loginError = MutableLiveData<String?>(null)
  val loginError: LiveData<String?> get() = _loginError

  fun login(ci: String, password: String, onLoginComplete: () -> Unit, onLoginError: (String) -> Unit) {
    loginRequest(ci, password) { success, response ->
      println("I'm here")
      if (success) {
        onLoginComplete()
      }
      else{
        println(response)
        onLoginError(response)
      }
    }

  }

  fun clearLoginError() {
    _loginError.value = null
  }

  fun loginInvitado(onLoginComplete: () -> Unit) {
    userRepository.loginInvitado()
    onLoginComplete()
  }
}

class LoginViewModelFactory: ViewModelProvider.Factory {
  @Suppress("UNCHECKED_CAST")
  override fun <T: ViewModel> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
      return LoginViewModel(UserRepository) as T
    }
    throw IllegalArgumentException("Unknown ViewModel class")
  }
}
