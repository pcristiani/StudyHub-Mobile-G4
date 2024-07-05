package com.example.compose.studyhub.ui.route

import alertDialogDoc2
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
import com.example.compose.studyhub.ui.screen.estudiante.InscripcionCarreraScreen
import com.example.compose.studyhub.viewModel.InscripcionCarreraViewModel
import com.example.compose.studyhub.viewModel.InscripcionCarreraViewModelFactory
import com.example.compose.studyhub.viewModel.RegisterViewModel
import com.example.compose.studyhub.viewModel.RegisterViewModelFactory

///
@Composable
fun InscripcionCarreraRoute(
    onInscripcionCarreraConfirmed: () -> Unit,
    onNavUp: () -> Unit,
) {


    val inscripcionCarreraViewModel: InscripcionCarreraViewModel = viewModel(factory = InscripcionCarreraViewModelFactory())

    var inscripcionCarreraError by remember { mutableStateOf<String?>(null) }
    var inscripcionCarreraSuccess by remember { mutableStateOf<String?>(null) }

    InscripcionCarreraScreen(
        onInscripcionCarreraSubmitted = {idCarrera->
            inscripcionCarreraViewModel.inscripcionCarrera(idCarrera, {it->inscripcionCarreraSuccess = it; inscripcionCarreraError = ""},{it->inscripcionCarreraError = it; inscripcionCarreraSuccess = ""})
        },

        onInscripcionCarreraConfirmed = onInscripcionCarreraConfirmed,

        onSuccess = inscripcionCarreraSuccess,

        onError = inscripcionCarreraError,

        onNavUp = onNavUp,
    )


}
