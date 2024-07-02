package com.example.compose.studyhub.ui.route

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.compose.studyhub.data.UserRepository
import com.example.compose.studyhub.domain.EmailState
import com.example.compose.studyhub.ui.component.DialogBoxCreation
import com.example.compose.studyhub.ui.component.DialogBoxWithTextCreation
import com.example.compose.studyhub.viewModel.LoginViewModelFactory
import com.example.compose.studyhub.viewModel.LogoutViewModel
import com.example.compose.studyhub.viewModel.LogoutViewModelFactory
import com.example.compose.studyhub.viewModel.RecoverPassViewModel
import com.example.compose.studyhub.viewModel.RecoverPassViewModelFactory

@Composable
fun RecoverPassRoute(onConfirmation: (String) -> Unit, dialogTitle: String, onDismiss: () -> Unit, onFail: (String) -> Unit) {
   val recoverPassViewModel: RecoverPassViewModel = viewModel(factory = RecoverPassViewModelFactory())

   DialogBoxWithTextCreation(
      onDismissRequest = onDismiss,
      onConfirmation = {
         recoverPassViewModel.recoverPass(email = it, onRecoverPasswordComplete = onConfirmation, onRecoverPasswordFail = onFail)
   }, dialogTitle = dialogTitle)
}
