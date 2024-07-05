package com.example.compose.studyhub.ui.screen.estudiante

import AsignaturaRequest
import HorariosAsignaturaRequest
import InscripcionAsignaturaRequest
import alertDialogDoc2
import android.annotation.SuppressLint
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
import com.example.compose.studyhub.http.requests.getAsignaturasDeCarreraRequest
import com.example.compose.studyhub.http.requests.getHorariosAsignaturaRequest
import com.example.compose.studyhub.http.requests.inscripcionAsignaturaRequest
import com.example.compose.studyhub.ui.component.CarreraCard
import com.example.compose.studyhub.ui.component.inscripciones.AsigaturaDeCarrera
import com.example.compose.studyhub.ui.component.inscripciones.CarrerasInscripto
import com.example.compose.studyhub.ui.component.inscripciones.HorariosAsignatura
import kotlinx.coroutines.launch


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun InscripcionAsignaturaScreen(
  onInscripcionAsignaturaSubmitted: (idAsignatura: Int, idHorario: Int) -> Unit,
  onInscripcionAsignaturaConfirmed: () -> Unit,
  onNavUp: () -> Unit,
  onError: String? = null,
  onSuccess: String? = null
): DrawerState {
  val remIdCarrera = remember { mutableStateOf<Int?>(null) }
  val remIdAsignatura= remember { mutableStateOf<Int?>(null) }
  val remIdHorario= remember { mutableStateOf<Int?>(null) }
  val scope = rememberCoroutineScope()
  val snackbarHostState = remember { SnackbarHostState() }
  var onRepeat by remember { mutableStateOf(false) }
  var showColumn by remember { mutableStateOf(false) }

  LaunchedEffect(onRepeat){
    remIdCarrera.value = null
    remIdAsignatura.value = null
    remIdHorario.value = null
    showColumn = true
    onRepeat=false
  }

  if(showColumn){
    Column(modifier = Modifier.padding(top = 50.dp, bottom = 1.dp)) {
      if (remIdCarrera.value == null) {
        CarrerasInscripto(modifier = Modifier.fillMaxWidth(),onHeaderClicked = { idC: Int? ->
          if (idC != null) {
            remIdCarrera.value = idC
            println("Este remIdCarrera : ${remIdCarrera.value}")
          }
        })
      } else if (remIdAsignatura.value == null && remIdCarrera.value != null) {
        AsigaturaDeCarrera(modifier = Modifier.fillMaxWidth(), carreraId = remIdCarrera.value !!,onHeaderClicked = { idC: Int? ->
          if (idC != null) {
            UserRepository.getToken()?.let { token ->
              getHorariosAsignaturaRequest(idC, token) { responde ->
                if(responde!=null){
                  remIdAsignatura.value = idC
                }
                else{
                  onRepeat = true
                  showColumn = false
                }
              }
            }

          }
        })
      } else if (remIdAsignatura.value != null) {
        HorariosAsignatura(modifier = Modifier.fillMaxWidth(), asignaturaId = remIdAsignatura.value !!,onHeaderClicked = { idC: Int? ->
          if (idC != null) {
            remIdHorario.value = idC
            println("Este remIdHorario : ${remIdHorario.value}")
          }
        })
      }
      if(remIdCarrera.value != null && remIdAsignatura.value != null && remIdHorario.value != null) {
        InscripcionAsignatura(onError = onError, onSuccess = onSuccess, idCarrera =remIdCarrera.value!!, idHorario =  remIdHorario.value !!, idAsignatura=remIdAsignatura.value !!, onInscripcionAsignaturaSubmitted = onInscripcionAsignaturaSubmitted, onInscripcionAsignaturaConfirmed = onInscripcionAsignaturaConfirmed, cancelled = {cancelled ->  showColumn = !cancelled; onRepeat=cancelled})
      }
    }
  }

  return DrawerState(DrawerValue.Closed)
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun InscripcionAsignatura(onError: String?, onSuccess: String?, idCarrera: Int, idHorario:Int, idAsignatura:Int,onInscripcionAsignaturaSubmitted: (idAsignatura: Int, idHorario: Int) -> Unit, onInscripcionAsignaturaConfirmed: () -> Unit, cancelled: (Boolean) -> Unit) {
  val coroutineScope = rememberCoroutineScope()
  val snackbarHostState = remember { SnackbarHostState() }
  var responsse by remember { mutableStateOf<String?>(null) }
  val showConfirmationDialog = remember { mutableStateOf(false) }
  val showErrorDialog = remember { mutableStateOf(false) }
  val retry = remember { mutableStateOf(false) }

  LaunchedEffect(onError, retry){
    if(onError!=null){
      if(onError==""){
        showConfirmationDialog.value = true
      }
      else{
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
  Scaffold(
    snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
  ) {
    if (showConfirmationDialog.value) {
      println("---------"+responsse)
      alertDialogDoc2(title = "¡Inscripción exitosa!", text = onSuccess ?: "",onHeaderClicked = {onInscripcionAsignaturaConfirmed() })
    }else{
      alertDialogDoc2(title = "¡Advertencia!", text = onError ?: "",onHeaderClicked = {cancelled(true)})
    }
  }
}

