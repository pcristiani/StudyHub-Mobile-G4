package com.example.compose.studyhub.ui.route

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.compose.studyhub.ui.screen.RegisterScreen
import com.example.compose.studyhub.viewModel.RegisterViewModel
import com.example.compose.studyhub.viewModel.RegisterViewModelFactory

///
@Composable
fun RegisterRoute(
    ci: String?,
    onRegisterSubmitted: () -> Unit,
    onNavUp: () -> Unit,
) {
    val signUpViewModel: RegisterViewModel = viewModel(factory = RegisterViewModelFactory())
    RegisterScreen(
        ci = ci,
        onRegisterSubmitted = { nombre, apellido, email, fechaNacimiento, ci, password ->
            signUpViewModel.signUp(nombre, apellido, email, fechaNacimiento, ci, password,onRegisterSubmitted)

        },
        onNavUp = onNavUp,
    )
}
