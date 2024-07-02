package com.example.compose.studyhub.ui.route

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.compose.studyhub.ui.screen.LoginScreen
import com.example.compose.studyhub.viewModel.LoginViewModel
import com.example.compose.studyhub.viewModel.LoginViewModelFactory

///
@Composable
fun LoginRoute(ci: String?, onLoginSubmitted: () -> Unit, onNavigateToRegister: (ci: String?) -> Unit, onNavUp: () -> Unit) {
   val loginViewModel: LoginViewModel = viewModel(factory = LoginViewModelFactory())
   var loginError by remember {mutableStateOf("")}

   LoginScreen(ci = ci, onLoginSubmitted = { ciS, password -> loginViewModel.login(ciS, password, onLoginSubmitted
   ) { it -> loginError = it }
   }, onNavigateToRegister = {onNavigateToRegister(ci)}, onNavUp = onNavUp, loginError = loginError, onErrorDismissed = {loginViewModel.clearLoginError()})
}
