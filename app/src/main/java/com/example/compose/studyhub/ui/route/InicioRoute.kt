package com.example.compose.studyhub.ui.route

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.compose.studyhub.ui.screen.InicioScreen
import com.example.compose.studyhub.viewModel.InicioViewModel
import com.example.compose.studyhub.viewModel.InicioViewModelFactory

///
@Composable
fun InicioRoute(
   onNavigateToLogin: (email: String) -> Unit,
   onNavigateToRegister: (email: String) -> Unit,
   onLoginInvitado: () -> Unit,
) {
   val inicioViewModel: InicioViewModel = viewModel(factory = InicioViewModelFactory())
   InicioScreen(
      onLoginRegister = { email ->
         inicioViewModel.handleContinue(
            email = email,
            onNavigateToLogin = onNavigateToLogin,
            onNavigateToRegister = onNavigateToRegister,
         )
      },
      onLoginInvitado = { inicioViewModel.handleInvitado(onLoginInvitado) },
   )
}
