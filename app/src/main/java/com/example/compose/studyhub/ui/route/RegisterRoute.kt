package com.example.compose.studyhub.ui.route

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.compose.studyhub.ui.screen.RegisterScreen
import com.example.compose.studyhub.viewModel.RegisterViewModel
import com.example.compose.studyhub.viewModel.RegisterViewModelFactory

///
@Composable
fun RegisterRoute(
    email: String?,
    onRegisterSubmitted: () -> Unit,
    onLoginInvitado: () -> Unit,
    onNavUp: () -> Unit,
) {
    val signUpViewModel: RegisterViewModel = viewModel(factory = RegisterViewModelFactory())
    RegisterScreen(
        email = email,
        onRegisterSubmitted = { email, password ->
            signUpViewModel.signUp(email, password, onRegisterSubmitted)
        },
        onLoginInvitado = { signUpViewModel.loginInvitado(onLoginInvitado) },
        onNavUp = onNavUp,
    )
}
