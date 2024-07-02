package com.example.compose.studyhub.ui.route

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.compose.studyhub.ui.screen.InicioScreen
import com.example.compose.studyhub.viewModel.InicioViewModel
import com.example.compose.studyhub.viewModel.InicioViewModelFactory

///
@Composable
fun InicioRoute(
   onNavigateToLogin: (ci: String) -> Unit,
   ) {
   val inicioViewModel: InicioViewModel = viewModel(factory = InicioViewModelFactory())
   InicioScreen(
      onLoginRegister = { ci ->
         inicioViewModel.handleContinue(ci = ci, onNavigateToLogin = onNavigateToLogin)
      })
}
