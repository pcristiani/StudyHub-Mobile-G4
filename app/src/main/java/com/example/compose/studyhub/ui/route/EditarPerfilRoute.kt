package com.example.compose.studyhub.ui.route

import com.example.compose.studyhub.ui.screen.estudiante.EditarPerfilScreen
import com.example.compose.studyhub.ui.navigation.NavRoutes.EditarPerfilScreen
import com.example.compose.studyhub.viewModel.EditarPerfilViewModel
import com.example.compose.studyhub.viewModel.EditarPerfilViewModelFactory

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.compose.studyhub.ui.screen.RegisterScreen
import com.example.compose.studyhub.viewModel.RegisterViewModel
import com.example.compose.studyhub.viewModel.RegisterViewModelFactory

///
@Composable
fun EditarPerfilRoute(
    onProfileEditSubmitted: () -> Unit,
    onNavUp: () -> Unit,
) {
    val EditarPerfilViewModel: EditarPerfilViewModel = viewModel(factory = EditarPerfilViewModelFactory())
    EditarPerfilScreen(
        onProfileEditSubmitted = { nombre, apellido, email, fechaNacimiento ->
            EditarPerfilViewModel.modifyProfile(nombre,apellido,email,fechaNacimiento, onProfileEditSubmitted)
    },
        onNavUp = onNavUp,
    )
}
