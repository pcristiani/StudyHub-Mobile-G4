package com.example.compose.studyhub.ui.route

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.compose.studyhub.ui.screen.LoginScreen
import com.example.compose.studyhub.viewModel.LoginViewModel
import com.example.compose.studyhub.viewModel.LoginViewModelFactory

///
@Composable
fun LoginRoute(ci: String?, onLoginSubmitted: () -> Unit, onNavigateToRegister: (ci: String?) -> Unit, onNavUp: () -> Unit) {
   val loginViewModel: LoginViewModel = viewModel(factory = LoginViewModelFactory())
   val loginError by loginViewModel.loginError.observeAsState()

   LoginScreen(ci = ci, onLoginSubmitted = { ci, password -> loginViewModel.login(ci, password, onLoginSubmitted) }, onNavigateToRegister = {onNavigateToRegister(ci)}, onNavUp = onNavUp, loginError = loginError, onErrorDismissed = {loginViewModel.clearLoginError()})
}
