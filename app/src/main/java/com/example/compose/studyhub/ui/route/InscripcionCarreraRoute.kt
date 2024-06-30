package com.example.compose.studyhub.ui.route

import com.example.compose.studyhub.ui.screen.estudiante.EditarPerfilScreen
import com.example.compose.studyhub.ui.navigation.NavRoutes.EditarPerfilScreen
import com.example.compose.studyhub.viewModel.EditarPerfilViewModel
import com.example.compose.studyhub.viewModel.EditarPerfilViewModelFactory

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
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

    onInscripcionCarreraSubmitted: () -> Unit,
    onNavUp: () -> Unit,
) {
    val inscripcionCarreraViewModel: InscripcionCarreraViewModel = viewModel(factory = InscripcionCarreraViewModelFactory())
    val inscripcionCarreraError by inscripcionCarreraViewModel.inscripcionCarreraError.observeAsState()



    InscripcionCarreraScreen(

        onInscripcionCarreraSubmitted = {idCarrera->
            inscripcionCarreraViewModel.inscripcionCarrera(idCarrera
            ){}


        },
        onInscripcionCarreraConfirmed = onInscripcionCarreraSubmitted,
        onError = inscripcionCarreraError,
        onNavUp = onNavUp,
    )

}
