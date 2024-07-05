package com.example.compose.studyhub.ui.route

import com.example.compose.studyhub.ui.screen.estudiante.EditarPerfilScreen
import com.example.compose.studyhub.ui.navigation.NavRoutes.EditarPerfilScreen
import com.example.compose.studyhub.viewModel.EditarPerfilViewModel
import com.example.compose.studyhub.viewModel.EditarPerfilViewModelFactory

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.compose.studyhub.ui.screen.RegisterScreen
import com.example.compose.studyhub.ui.screen.estudiante.InscripcionAsignaturaScreen
import com.example.compose.studyhub.ui.screen.estudiante.InscripcionCarreraScreen
import com.example.compose.studyhub.viewModel.InscripcionAsignaturaViewModel
import com.example.compose.studyhub.viewModel.InscripcionAsignaturaViewModelFactory
import com.example.compose.studyhub.viewModel.InscripcionCarreraViewModel
import com.example.compose.studyhub.viewModel.InscripcionCarreraViewModelFactory
import com.example.compose.studyhub.viewModel.RegisterViewModel
import com.example.compose.studyhub.viewModel.RegisterViewModelFactory

///
@Composable
fun InscripcionAsignaturaRoute(
    onInscripcionAsignaturaConfirmed: () -> Unit,
    onNavUp: () -> Unit,
) {
    val inscripcionAsignaturaViewModel: InscripcionAsignaturaViewModel = viewModel(factory = InscripcionAsignaturaViewModelFactory())
    var inscripcionAsignaturaError by remember { mutableStateOf<String?>(null) }
    var inscripcionAsignaturaSuccess by remember { mutableStateOf<String?>(null) }

    InscripcionAsignaturaScreen(
        onInscripcionAsignaturaSubmitted = {idAsignatura, idHorario ->
            inscripcionAsignaturaViewModel.inscripcionAsignatura(idAsignatura, idHorario, {it->inscripcionAsignaturaSuccess = it; inscripcionAsignaturaError = ""},{it->inscripcionAsignaturaError = it; inscripcionAsignaturaSuccess = ""})
        },

        onInscripcionAsignaturaConfirmed = onInscripcionAsignaturaConfirmed,

        onSuccess = inscripcionAsignaturaSuccess,

        onError = inscripcionAsignaturaError,

        onNavUp = onNavUp,
    )

}
