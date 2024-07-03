package com.example.compose.studyhub.ui.screen.estudiante

import AsignaturaRequest
import ExamenRequest
import InscripcionExamenRequest
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
  val remIdAsignatura= remember { mutableStateOf<Int?>(null) }
  val remIdHorario= remember { mutableStateOf<Int?>(null) }

  Column(modifier = Modifier.padding(top = 50.dp, bottom = 1.dp)) {
    if (remIdCarrera.value == null) {
      CarrerasInscripto(modifier = Modifier.fillMaxWidth(), onHeaderClicked = { idC: Int? ->
        if (idC != null) {
          remIdCarrera.value = idC
          println("Este remIdCarrera : ${remIdCarrera.value}")
        }
      })
    } else if (remIdAsignatura.value == null) {
      AsigaturaConExamenPendiente(modifier = Modifier.fillMaxWidth(), carreraId = remIdCarrera.value !!,onHeaderClicked = { idC: Int? ->
        if (idC != null) {
          remIdAsignatura.value = idC
        }
      })
    } else {
      ExamenesDeAsignatura(modifier = Modifier.fillMaxWidth(), asignaturaId = remIdAsignatura.value !!,onHeaderClicked = { idC: Int? ->
        if (idC != null) {
          remIdHorario.value = idC
          println("Este remIdHorario : ${remIdHorario.value}")
        }else{
          println("Este remIdHorario : ${remIdHorario.value}")
        }
      })
    }
    if(remIdCarrera.value != null && remIdAsignatura.value != null && remIdHorario.value != null) {
      InscripcionExamen(carreraId =remIdCarrera.value!!, horarioId =  remIdHorario.value !!,idAsig=remIdAsignatura.value !!)
    }
  }
  return DrawerState(DrawerValue.Closed)
}


@Composable
fun InscripcionExamen(carreraId: Int, horarioId:Int, idAsig:Int) {
  val coroutineScope = rememberCoroutineScope()
  var checked by remember { mutableStateOf(true) }
  LaunchedEffect(horarioId) {
    coroutineScope.launch {
      UserRepository.loggedInUser()?.let { id ->
        UserRepository.getToken()?.let { token ->
          println("INCRIPCIONS "+id+" "+idAsig+" "+horarioId)

          if (checked) {
        //  inscripcionExamenRequest(token, inscripcionExamenRequest(1,1)) { responde -> }
            inscripcionExamenRequest(token, InscripcionExamenRequest(id, horarioId)) { success,responde ->
            }
          }
        }
      }
    }
  }
}


