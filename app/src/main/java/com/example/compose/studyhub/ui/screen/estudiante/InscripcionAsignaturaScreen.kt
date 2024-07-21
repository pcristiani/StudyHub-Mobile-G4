package com.example.compose.studyhub.ui.screen.estudiante

import alertDialogDoc2
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.compose.studyhub.data.UserRepository
import com.example.compose.studyhub.http.requests.getHorariosAsignaturaRequest
import com.example.compose.studyhub.ui.component.inscripciones.AsigaturaDeCarrera
import com.example.compose.studyhub.ui.component.inscripciones.CarrerasInscripto
import com.example.compose.studyhub.ui.component.inscripciones.HorariosAsignatura
import kotlinx.coroutines.launch


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun InscripcionAsignaturaScreen(
    onInscripcionAsignaturaSubmitted: (idAsignatura: Int, idHorario: Int) -> Unit, onInscripcionAsignaturaConfirmed: () -> Unit, onNavUp: () -> Unit, onError: String? = null, onSuccess: String? = null): DrawerState {
    val remIdCarrera = remember { mutableStateOf<Int?>(null) }
    val remIdAsignatura = remember { mutableStateOf<Int?>(null) }
    val remIdHorario = remember { mutableStateOf<Int?>(null) }
    var onRepeat by remember { mutableStateOf(false) }
    var showColumn by remember { mutableStateOf(false) }

    LaunchedEffect(onRepeat) {
        remIdCarrera.value = null
        remIdAsignatura.value = null
        remIdHorario.value = null
        showColumn = true
        onRepeat = false
    }

    if (showColumn) {
        Column(modifier = Modifier.padding(top = 50.dp, bottom = 30.dp)) {
            if (remIdCarrera.value == null) {
                CarrerasInscripto(modifier = Modifier.fillMaxWidth(), onHeaderClicked = { idC: Int? ->
                    if (idC != null) {
                        remIdCarrera.value = idC
                    }
                })
            } else if (remIdAsignatura.value == null && remIdCarrera.value != null) {
                AsigaturaDeCarrera(modifier = Modifier.fillMaxWidth(), carreraId = remIdCarrera.value!!, onHeaderClicked = { idC: Int? ->
                    if (idC != null) {
                        UserRepository.getToken()?.let { token ->
                            getHorariosAsignaturaRequest(idC, token) { response ->
                                if (response != null) {
                                    remIdAsignatura.value = idC
                                } else {
                                    onRepeat = true
                                    showColumn = false
                                }
                            }
                        }
                    }
                })
            } else if (remIdAsignatura.value != null) {
                HorariosAsignatura(modifier = Modifier.fillMaxWidth(), asignaturaId = remIdAsignatura.value!!, onHeaderClicked = { idC: Int? ->
                    if (idC != null) {
                        remIdHorario.value = idC
                        println("Este remIdHorario : ${remIdHorario.value}")
                    }
                })
            }
            if (remIdCarrera.value != null && remIdAsignatura.value != null && remIdHorario.value != null) {
                InscripcionAsignatura(onError = onError, onSuccess = onSuccess, idCarrera = remIdCarrera.value!!, idHorario = remIdHorario.value!!, idAsignatura = remIdAsignatura.value!!, onInscripcionAsignaturaSubmitted = onInscripcionAsignaturaSubmitted, onInscripcionAsignaturaConfirmed = onInscripcionAsignaturaConfirmed, cancelled = { cancelled -> showColumn = !cancelled; onRepeat = cancelled })
            }
        }
    }
    return DrawerState(DrawerValue.Closed)
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun InscripcionAsignatura(onError: String?, onSuccess: String?, idCarrera: Int, idHorario: Int, idAsignatura: Int, onInscripcionAsignaturaSubmitted: (idAsignatura: Int, idHorario: Int) -> Unit, onInscripcionAsignaturaConfirmed: () -> Unit, cancelled: (Boolean) -> Unit) {
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val showConfirmationDialog = remember { mutableStateOf(false) }
    val showErrorDialog = remember { mutableStateOf(false) }
    val retry = remember { mutableStateOf(false) }

    LaunchedEffect(onError, retry) {
        if (onError != null) {
            if (onError == "") {
                showConfirmationDialog.value = true
            } else {
                println("On error: $onError")
                showErrorDialog.value = true
            }
        }
    }

    LaunchedEffect(idHorario) {
        coroutineScope.launch {
            onInscripcionAsignaturaSubmitted(idAsignatura, idHorario)
        }
    }
    Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) {
        if (showConfirmationDialog.value) {
            alertDialogDoc2(title = "¡Inscripción exitosa!", text = onSuccess ?: "", onHeaderClicked = { onInscripcionAsignaturaConfirmed() })
        } else {
            alertDialogDoc2(title = "¡Advertencia!", text = onError ?: "", onHeaderClicked = { cancelled(true) })
        }
    }
}

