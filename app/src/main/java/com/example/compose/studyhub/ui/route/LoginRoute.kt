package com.example.compose.studyhub.ui.route

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.compose.studyhub.ui.screen.LoginScreen
import com.example.compose.studyhub.viewModel.LoginViewModel
import com.example.compose.studyhub.viewModel.LoginViewModelFactory

///
@Composable
fun LoginRoute(ci: String?, onLoginSubmitted: () -> Unit, onNavigateToRegister: (ci: String?) -> Unit, onLoginInvitado: () -> Unit, onNavUp: () -> Unit) {
   val loginViewModel: LoginViewModel = viewModel(factory = LoginViewModelFactory())


   LoginScreen(ci = ci, onLoginSubmitted = { ci, password -> loginViewModel.login(ci, password, onLoginSubmitted) }, onNavigateToRegister = {onNavigateToRegister(ci)}, onLoginInvitado = { loginViewModel.loginInvitado(onLoginInvitado) }, onNavUp = onNavUp)
}
