package com.example.compose.studyhub.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.compose.studyhub.data.UserRepository

///
class InicioViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun handleContinue(email: String, onNavigateToLogin:(email: String) -> Unit, onNavigateToRegister: (email: String) -> Unit,) {
        if (userRepository.existeUserEmail(email)) {
          //  println("> TRUE")
            onNavigateToLogin(email)
        } else {
          //  println("> FALSE")
            onNavigateToRegister(email)
        }
    }

    fun handleInvitado(onLoginComplete: () -> Unit,) {
        userRepository.loginInvitado()
        onLoginComplete()
    }
}

///
class InicioViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InicioViewModel::class.java)) {
            return InicioViewModel(UserRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
