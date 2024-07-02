package com.example.compose.studyhub.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.compose.studyhub.data.UserRepository
import com.example.compose.studyhub.http.requests.cerrarSesionRequest
import com.example.compose.studyhub.http.requests.forgotPasswordRequest

class RecoverPassViewModel(userRepository: UserRepository) : ViewModel() {
    fun recoverPass(email: String, onRecoverPasswordComplete: (String) -> Unit, onRecoverPasswordFail:(String)-> Unit ) {
        forgotPasswordRequest(email) {state, response ->
            if(state){
                onRecoverPasswordComplete(response)
            }
            else{
                onRecoverPasswordFail(response)
            }
        }
    }
}

class RecoverPassViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecoverPassViewModel::class.java)) {
            return RecoverPassViewModel(UserRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
