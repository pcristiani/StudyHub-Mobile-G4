package com.example.compose.studyhub.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.compose.studyhub.data.UserRepository

///
class InicioViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun handleContinue(ci: String, onNavigateToLogin:(email: String) -> Unit, onNavigateToRegister: (email: String) -> Unit,) {


            if (userRepository.existeUserCi(ci)) {
                println("> TRUE")
                onNavigateToLogin(ci)
            } else {
                println("> FALSE")
                onNavigateToLogin(ci)
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
