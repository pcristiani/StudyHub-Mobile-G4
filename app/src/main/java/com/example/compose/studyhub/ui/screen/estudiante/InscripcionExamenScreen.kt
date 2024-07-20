package com.example.compose.studyhub.ui.screen.estudiante

import AsignaturaRequest
import CarreraRequest
import ExamenRequest
import InscripcionExamenRequest
import alertDialogDoc2
import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.compose.studyhub.R
import com.example.compose.studyhub.R.string.txt_selectAsignatura
import com.example.compose.studyhub.R.string.txt_selectHorario
import com.example.compose.studyhub.data.UserRepository
import com.example.compose.studyhub.http.requests.getAsignaturasConExamenPendienteRequest
import com.example.compose.studyhub.http.requests.getExamenesAsignatura
import com.example.compose.studyhub.http.requests.inscripcionExamenRequest
import com.example.compose.studyhub.ui.component.CarreraCard
import com.example.compose.studyhub.ui.component.ConfirmDialogBox
import com.example.compose.studyhub.ui.component.HorarioCard
import com.example.compose.studyhub.ui.component.inscripciones.AsigaturaConExamenPendiente
import com.example.compose.studyhub.ui.component.inscripciones.CarrerasInscripto
import com.example.compose.studyhub.ui.component.inscripciones.ExamenesDeAsignatura
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun InscripcionExamenScreen(): DrawerState {
    val remIdCarrera = remember { mutableStateOf<Int?>(null) }
    val remIdAsignatura = remember { mutableStateOf<Int?>(null) }
    val remIdHorario = remember { mutableStateOf<Int?>(null) }

    Column(modifier = Modifier.padding(top = 50.dp, bottom = 30.dp)) {
        if (remIdCarrera.value == null) {
            CarrerasInscripto(modifier = Modifier.fillMaxWidth(), onHeaderClicked = { idC: Int? ->
                if (idC != null) {
                    remIdCarrera.value = idC
                    println("Este remIdCarrera : ${remIdCarrera.value}")
                }
            })
        } else if (remIdAsignatura.value == null) {
            AsigaturaConExamenPendiente(modifier = Modifier.fillMaxWidth(), carreraId = remIdCarrera.value!!, onHeaderClicked = { idC: Int? ->
                if (idC != null) {
                    remIdAsignatura.value = idC
                }
            })
        } else {
            ExamenesDeAsignatura(modifier = Modifier.fillMaxWidth(), asignaturaId = remIdAsignatura.value!!, onHeaderClicked = { idC: Int? ->
                if (idC != null) {
                    remIdHorario.value = idC
                    println("Este remIdHorario : ${remIdHorario.value}")
                } else {
                    println("Este remIdHorario : ${remIdHorario.value}")
                }
            })
        }
        if (remIdCarrera.value != null && remIdAsignatura.value != null && remIdHorario.value != null) {
            InscripcionExamen(carreraId = remIdCarrera.value!!, horarioId = remIdHorario.value!!, idAsig = remIdAsignatura.value!!, onSuccess = "Inscripción exitosa", onError = "Ya te encuentras inscripto en este examen",
                onInscripcionAsignaturaConfirmed = {
                remIdCarrera.value = null
                remIdAsignatura.value = null
                remIdHorario.value = null
            }, cancelled = {
                remIdCarrera.value = null
                remIdAsignatura.value = null
                remIdHorario.value = null
            })
        }
    }
    return DrawerState(DrawerValue.Closed)
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun InscripcionExamen(carreraId: Int, horarioId: Int, idAsig: Int, onSuccess: String?, onError: String?, onInscripcionAsignaturaConfirmed: () -> Unit = {}, cancelled: (Boolean) -> Unit = {}) {
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val showConfirmationDialog = remember { mutableStateOf(false) }
    val showErrorDialog = remember { mutableStateOf(false) }
    val retry = remember { mutableStateOf(false) }
    var responseExamen by remember { mutableStateOf<String?>(null) }

    var checked by remember { mutableStateOf(true) }
    LaunchedEffect(horarioId) {
        coroutineScope.launch {
            UserRepository.loggedInUser()?.let { id ->
                UserRepository.getToken()?.let { token ->

                    if (checked) {
                        inscripcionExamenRequest(token, InscripcionExamenRequest(id, horarioId)) { success, responde ->
                            if (success) {
                                if (responde != null) {
                                    println("Inscripcion exitosa: $responde")
                                    responseExamen = responde
                                }
                                showConfirmationDialog.value = true
                            } else {
                                println("Inscripcion fallida")
                                if (responde != null) {
                                    println("Error: $responde")
                                    responseExamen = responde
                                }
                                showErrorDialog.value = true

                            }

                        }
                    }
                }
            }
        }

        }
 /*       LaunchedEffect(onError, retry) {
            if (onError != null) {
                if (onError == "") {
                    showConfirmationDialog.value = true
                } else {
                    println("On error: $onError")
                    showErrorDialog.value = true
                }
            }
        }*/
    if(showConfirmationDialog.value){
        ConfirmDialogBox(onDismissRequest = { onInscripcionAsignaturaConfirmed()
            ; showConfirmationDialog.value = false }, dialogTitle = responseExamen?:"")
    }
    if(showErrorDialog.value){
        ConfirmDialogBox(onDismissRequest = { cancelled(true)
            ; showErrorDialog.value = false }, dialogTitle = responseExamen ?: "")
    }
     /*   Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) {
            println("$responseExamen")
          //  if (showConfirmationDialog.value) {
                alertDialogDoc2(title = "¡Inscripción exitosa!", text = responseExamen ?:"", onHeaderClicked = { onInscripcionAsignaturaConfirmed() })
          //  } else {
                alertDialogDoc2(title = "¡Advertencia!", text = responseExamen ?: "", onHeaderClicked = { cancelled(true) })
          //  }
        }*/

    }

