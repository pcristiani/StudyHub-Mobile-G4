package com.example.compose.studyhub.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.compose.studyhub.data.UserRepository

///
class RegisterViewModel(private val userRepository: UserRepository) : ViewModel() {

    // Considere todos los registros exitosos
    fun signUp(email: String, password: String, onRegisterComplete: () -> Unit,) {
        userRepository.signUp(email, password)
        onRegisterComplete()
    }

    fun loginInvitado(onLoginComplete: () -> Unit,) {
        userRepository.loginInvitado()
        onLoginComplete()
    }
}

///
class RegisterViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(UserRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
