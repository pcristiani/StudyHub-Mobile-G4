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
import com.example.compose.studyhub.ui.component.inscripciones.CarrerasInscripto
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





//////////////////////////////////////////////////////////////////////////////////////


// ASIGNATURA CON EXAMEN PENDIENTE
@Composable
fun firstLoad4(checked: Boolean,idC: Int): List<AsignaturaRequest>? {
  var listAsignaturas by remember { mutableStateOf<List<AsignaturaRequest>?>(null) }
  LaunchedEffect(checked) {
    UserRepository.loggedInUser()?.let { id ->
      UserRepository.getToken()?.let { token ->
        if (checked) {
          getAsignaturasConExamenPendienteRequest(id,idC,token) { success ->
            listAsignaturas = success
          }
        }
      }
    }
  }
  return listAsignaturas
}


@Composable
fun AsigaturaConExamenPendiente(modifier: Modifier, carreraId:Int, onHeaderClicked: (Int) -> Unit) {
  val nombreAsignaturaList = remember { mutableStateListOf<AsignaturaRequest>() }
  val isLoading = remember { mutableStateOf(false) }
  val listState = rememberLazyListState()
  val coroutineScope = rememberCoroutineScope()
  var asignaturas by remember { mutableStateOf<List<AsignaturaRequest>?>(null) }
  var checked by remember { mutableStateOf(true) }

  asignaturas = firstLoad4(checked, carreraId)
  LaunchedEffect(asignaturas) {
    nombreAsignaturaList.clear()
    asignaturas?.let {
      loadMoreAsignaturaExamenPendiente(nombreAsignaturaList, it)
    }
  }

  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(top = 60.dp, bottom = 1.dp),
    verticalArrangement = Arrangement.spacedBy(20.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    Text(
      text = stringResource(id = txt_selectAsignatura),
      style = MaterialTheme.typography.headlineSmall,
    )

    if (asignaturas != null) {
      LazyColumn(state = listState, modifier = Modifier
        .weight(1f)
        .padding(bottom = 20.dp)) {
        items(nombreAsignaturaList.size) { index ->
          AsignaturaExamPendienteItem(user = nombreAsignaturaList[index].nombre, idC = nombreAsignaturaList[index].idAsignatura) {
            onHeaderClicked(it)
          }
        }
        if (isLoading.value) {
          item {
            Box(modifier = Modifier
              .fillMaxWidth()
              .padding(16.dp)) {
            }
          }
        }
      }
     /* LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }.collect { index ->
          if (index == nombreAsignaturaList.size - 1 && ! isLoading.value && nombreAsignaturaList.size <= asignaturas !!.size) {
            isLoading.value = true
            coroutineScope.launch {
              delay(3000)
              loadMoreAsignaturaExamenPendiente(nombreAsignaturaList, asignaturas !!)
              isLoading.value = false
            }
          }
        }
      }*/
    } else {
      Text(text = stringResource(id = R.string.txt_error_solicitudes), textAlign = TextAlign.Center)
    }
  }
}


fun loadMoreAsignaturaExamenPendiente(asignaturasList: MutableList<AsignaturaRequest>, asignaturas: List<AsignaturaRequest>) {
  val currentSize = asignaturasList.size
  val listLength = if ((asignaturas.size - currentSize) < 30) {
    (asignaturas.size - currentSize)
  } else {
    30
  }
  for (i in 0 until listLength) {
    asignaturasList.add(asignaturas[currentSize + i])
  }
}

@Composable
fun AsignaturaExamPendienteItem(user: String, idC: Int, onSelected: (Int) -> Unit) {
  CarreraCard(nombre = user, onHeaderClicked = { onSelected(idC) })
}




////////////////////////////////////////////////////////////


// EXAMENES DE ASIGNATURA
@Composable
fun firstLoad5(checked: Boolean,asignaturaId:Int): List<ExamenRequest>? {
  var carreras by remember { mutableStateOf<List<ExamenRequest>?>(null) }
  LaunchedEffect(checked) {
    UserRepository.loggedInUser()?.let { id ->
      UserRepository.getToken()?.let { token ->
        if (checked) {
          println(id)
          getExamenesAsignatura(asignaturaId,token) { responde ->
            println(responde)
            carreras = responde
          }
        }
      }
    }
  }
  return carreras
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ExamenesDeAsignatura(modifier: Modifier, asignaturaId:Int, onHeaderClicked: (Int) -> Unit) {
  val nombreCarrerasList = remember { mutableStateListOf<ExamenRequest>() }
  val isLoading = remember { mutableStateOf(false) }
  val listState = rememberLazyListState()
  val coroutineScope = rememberCoroutineScope()
  var carreras by remember { mutableStateOf<List<ExamenRequest>?>(null) }
  var checked by remember { mutableStateOf(true) }

  carreras = firstLoad5(checked,asignaturaId)
  LaunchedEffect(carreras) {
    nombreCarrerasList.clear()
    carreras?.let {
      loadMoreExamenesDeAsignatura(nombreCarrerasList, it)
    }
  }

  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(top = 60.dp, bottom = 1.dp), verticalArrangement = Arrangement.spacedBy(20.dp), horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    Text(
      text = stringResource(id = txt_selectHorario),
      style = MaterialTheme.typography.headlineSmall,
    )

    if (carreras != null) {
      LazyColumn(state = listState, modifier = Modifier
        .weight(1f)
        .padding(bottom = 20.dp)) {
        items(nombreCarrerasList.size) { index ->
             ExamenesDeAsignaturaItem(user =  "Periodo " + nombreCarrerasList.get(index).periodoExamen + "   -   " + format(nombreCarrerasList.get(index).fechaHora),
            idC = nombreCarrerasList[index].idExamen) {
            onHeaderClicked(it)
          }
        }
        if (isLoading.value) {
          item {
            Box(modifier = Modifier
              .fillMaxWidth()
              .padding(16.dp)) { //   CircularProgressIndicator()
            }
          }
        }
      }
     /* LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }.collect { index ->
          if (index == nombreCarrerasList.size - 1 && ! isLoading.value && nombreCarrerasList.size <= carreras !!.size) {
            isLoading.value = true
            coroutineScope.launch {
              delay(3000)
              loadMoreExamenesDeAsignatura(nombreCarrerasList, carreras !!)
              isLoading.value = false
            }
          }
        }
      }*/
    } else {
      Text(text = stringResource(id = R.string.txt_error_horario), textAlign = TextAlign.Center)
    }
  }
}

fun loadMoreExamenesDeAsignatura(carrerasList: MutableList<ExamenRequest>, carreras: List<ExamenRequest>) {
  val currentSize = carrerasList.size
  val listLength = if ((carreras.size - currentSize) < 30) {
    (carreras.size - currentSize)
  } else {
    30
  }
  for (i in 0 until listLength) {
    carrerasList.add(carreras[currentSize + i])
  }
}

@RequiresApi(Build.VERSION_CODES.O)
fun format(fechaStr: String): String {
  val formatterEntrada = DateTimeFormatter.ISO_LOCAL_DATE_TIME
  val fecha = LocalDateTime.parse(fechaStr, formatterEntrada)
  val formatterSalida = DateTimeFormatter.ofPattern("dd/MM/yyyy'  'HH:mm")
  val fechaFormateada = fecha.format(formatterSalida)

  println(fechaFormateada)
  return fechaFormateada + "hs"
}

@Composable
fun ExamenesDeAsignaturaItem(user: String, idC: Int, onSelected: (Int) -> Unit) {
  HorarioCard(nombre = user, onHeaderClicked = { onSelected(idC) })
}
