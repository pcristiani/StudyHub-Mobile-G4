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
  /*onInscripcionAsignaturaSubmitted: (idCarrera: Int) -> Unit,
  onInscripcionAsignaturaConfirmed: () -> Unit,
  onNavUp: () -> Unit,
  onError: String? = null,
  onSuccess: String? = null*/
): DrawerState {
  val remIdCarrera = remember { mutableStateOf<Int?>(null) }
  val remIdAsignatura= remember { mutableStateOf<Int?>(null) }
  val remIdHorario= remember { mutableStateOf<Int?>(null) }
  val scope = rememberCoroutineScope()
  val snackbarHostState = remember { SnackbarHostState() }

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
      InscripcionAsignatura(carreraId =remIdCarrera.value!!, horarioId =  remIdHorario.value !!, idAsig=remIdAsignatura.value !!)
    }
  }
  return DrawerState(DrawerValue.Closed)
}

fun seleccion(idCarrera: Int, idAsignatura: Int, idHorario: Int){

}



@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun InscripcionAsignatura(carreraId: Int, horarioId:Int, idAsig:Int) {
  val coroutineScope = rememberCoroutineScope()
  var checked by remember { mutableStateOf(true) }
  val snackbarHostState = remember { SnackbarHostState() }
  val scope = rememberCoroutineScope()
  var responsse by remember { mutableStateOf<String?>(null) }

  LaunchedEffect(horarioId) {
    coroutineScope.launch {
      UserRepository.loggedInUser()?.let { id ->
        UserRepository.getToken()?.let { token ->
          println("INCRIPCIONS " + id + " " + idAsig + " " + horarioId)

          if (checked) {
            inscripcionAsignaturaRequest(token, InscripcionAsignaturaRequest(id, idAsig , horarioId)) { success, responde ->
              if (success) {
                scope.launch {
                    snackbarHostState.showSnackbar("$responde")
                    responsse = "$responde"
                }
              } else {
                scope.launch {
                    snackbarHostState.showSnackbar("$responde")
                    responsse = "$responde"
                }
              }
            }
          }
        }
      }
    }
  }
  Scaffold(
    snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
  ) {
    if (responsse != null && responsse != "null") {
      println("---------"+responsse)
      alertDialogDoc2(title = "¡Inscripción exitosa!", text = responsse ?: "",onHeaderClicked = { responsse = null })
    }else{  //    alertDialogDoc2(title = "¡Advertencia!", text = responsse ?: "",onHeaderClicked = { responsse = null })
    }
  }
}

