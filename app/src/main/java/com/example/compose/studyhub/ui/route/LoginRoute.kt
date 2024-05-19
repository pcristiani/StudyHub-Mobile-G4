package com.example.compose.studyhub.ui.route

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.compose.studyhub.ui.screen.LoginScreen
import com.example.compose.studyhub.viewModel.LoginViewModel
import com.example.compose.studyhub.viewModel.LoginViewModelFactory

///
@Composable
fun LoginRoute(email: String?, onLoginSubmitted: () -> Unit, onLoginInvitado: () -> Unit, onNavUp: () -> Unit) {
   val loginViewModel: LoginViewModel = viewModel(factory = LoginViewModelFactory())
   LoginScreen(email = email, onLoginSubmitted = { email, password -> loginViewModel.login(email, password, onLoginSubmitted) }, onLoginInvitado = { loginViewModel.loginInvitado(onLoginInvitado) }, onNavUp = onNavUp)
}
