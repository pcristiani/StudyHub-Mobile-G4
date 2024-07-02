package com.example.compose.studyhub.ui.route

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.compose.studyhub.data.UserRepository
import com.example.compose.studyhub.ui.component.DialogBoxCreation
import com.example.compose.studyhub.viewModel.LoginViewModelFactory
import com.example.compose.studyhub.viewModel.LogoutViewModel
import com.example.compose.studyhub.viewModel.LogoutViewModelFactory

///
@Composable
fun LogoutRoute(onConfirmation: () -> Unit, dialogTitle: String, dialogText: String, onDismiss: () -> Unit) {
   val logoutViewModel: LogoutViewModel = viewModel(factory = LogoutViewModelFactory())
   val token = UserRepository.getToken()

   DialogBoxCreation(
      onDismissRequest = onDismiss,
      onConfirmation = {
      if (token != null) {
         logoutViewModel.logout(token, onLogoffComplete = onConfirmation)
      }
   }, dialogTitle = dialogTitle, dialogText = dialogText)
}
