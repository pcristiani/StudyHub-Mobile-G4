package com.example.compose.studyhub.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.compose.studyhub.data.UserRepository
import com.example.compose.studyhub.http.requests.cerrarSesionRequest

class LogoutViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun logout(token: String, onLogoffComplete: () -> Unit) {
        
        cerrarSesionRequest(token) {success ->
            if(success){
                UserRepository.logout()
                onLogoffComplete()
            }
        }

    }

}

class LogoutViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LogoutViewModel::class.java)) {
            return LogoutViewModel(UserRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
