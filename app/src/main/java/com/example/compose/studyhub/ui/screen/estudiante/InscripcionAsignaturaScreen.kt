package com.example.compose.studyhub.ui.screen.estudiante

import AsignaturaRequest
import CarreraRequest
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
import com.example.compose.studyhub.R.string.txt_inscripciones
import com.example.compose.studyhub.R.string.txt_selectAsignatura
import com.example.compose.studyhub.R.string.txt_selectCarrera
import com.example.compose.studyhub.R.string.txt_selectHorario
import com.example.compose.studyhub.data.UserRepository
import com.example.compose.studyhub.http.requests.getAsignaturasDeCarreraRequest
import com.example.compose.studyhub.http.requests.getCarrerasRequest
import com.example.compose.studyhub.http.requests.getHorariosAsignaturaRequest
import com.example.compose.studyhub.http.requests.inscripcionAsignaturaRequest
import com.example.compose.studyhub.http.requests.inscripcionesCarreraRequest
import com.example.compose.studyhub.ui.component.CarreraCard
import com.example.compose.studyhub.ui.component.searchBar.LocalAccountsDataProvider
import com.example.compose.studyhub.ui.component.searchBar.SearchBarScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun InscripcionAsignaturaScreen(): DrawerState {
  val remIdCarrera = remember { mutableStateOf<Int?>(null) }
  val remIdAsignatura= remember { mutableStateOf<Int?>(null) }
  val remIdHorario= remember { mutableStateOf<Int?>(null) }
  val scope = rememberCoroutineScope()
  val snackbarHostState = remember { SnackbarHostState() }

  Column(modifier = Modifier.padding(top = 50.dp, bottom = 1.dp)) {
    if (remIdCarrera.value == null) {
      CarrerasInscripto(modifier = Modifier.fillMaxWidth(), snackbarHostState, scope, onHeaderClicked = { idC: Int? ->
        if (idC != null) {
          remIdCarrera.value = idC
          println("Este remIdCarrera : ${remIdCarrera.value}")
        }
      })
    } else if (remIdAsignatura.value == null && remIdCarrera.value != null) {
      AsigaturaDeCarrera(modifier = Modifier.fillMaxWidth(), carreraId = remIdCarrera.value !!,onHeaderClicked = { idC: Int? ->
        if (idC != null) {
          remIdAsignatura.value = idC
        }
      })
    } else {
      HorarioAsignatura(modifier = Modifier.fillMaxWidth(), asignaturaId = remIdAsignatura.value !!,onHeaderClicked = { idC: Int? ->
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




////////////////////////////////////////////////////////////////////////////////


// CARRERAS INSCRIPTO
@Composable
fun firstLoad3(checked: Boolean): List<CarreraRequest>? {
  var carreras by remember { mutableStateOf<List<CarreraRequest>?>(null) }
  LaunchedEffect(checked) {
    UserRepository.loggedInUser()?.let { id ->
      UserRepository.getToken()?.let { token ->
        if (checked) {
          println(id)
          inscripcionesCarreraRequest(id,token) { success ->
            carreras = success
          }
        }
      }
    }
  }
  return carreras
}


@Composable
fun CarrerasInscripto(modifier: Modifier, snackbarHostState:SnackbarHostState, scope: CoroutineScope, onHeaderClicked: (Int) -> Unit) {
  val nombreCarrerasList = remember { mutableStateListOf<CarreraRequest>() }
  val isLoading = remember { mutableStateOf(false) }
  val listState = rememberLazyListState()
  val coroutineScope = rememberCoroutineScope()
  var carreras by remember { mutableStateOf<List<CarreraRequest>?>(null) }
  var checked by remember { mutableStateOf(true) }

  carreras = firstLoad3(checked)
  LaunchedEffect(carreras) {
    nombreCarrerasList.clear()
    carreras?.let {
      loadMoreAsigDeCarrera(nombreCarrerasList, it)
    }
  }

  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(top = 60.dp, bottom = 1.dp),
    verticalArrangement = Arrangement.spacedBy(20.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    SearchBarScreen(
      emails = LocalAccountsDataProvider.allUserAccounts,
      modifier = Modifier,
      navigateToDetail = { _, _ -> }
    )
    Text(
      text = stringResource(id = txt_selectCarrera),
      style = MaterialTheme.typography.headlineSmall,
    )

    if (carreras != null) {
      LazyColumn(state = listState, modifier = Modifier
        .weight(1f)
        .padding(bottom = 20.dp)) {
        items(nombreCarrerasList.size) { index ->
          AsigDeCarreraItem(user = nombreCarrerasList[index].nombre, snackbarHostState, scope, idC = nombreCarrerasList[index].idCarrera) {
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
    /*  LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }.collect { index ->
          if (index == nombreCarrerasList.size - 1 && ! isLoading.value && nombreCarrerasList.size <= carreras !!.size) {
            isLoading.value = true
            coroutineScope.launch {
              delay(10)
              loadMoreAsigDeCarrera(nombreCarrerasList, carreras !!)
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


fun loadMoreAsigDeCarrera(carrerasList: MutableList<CarreraRequest>, carreras: List<CarreraRequest>) {
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


@Composable
fun AsigDeCarreraItem(user: String, snackbarHostState:SnackbarHostState, scope:CoroutineScope, idC: Int, onSelected: (Int) -> Unit) {
  CarreraCard(nombre = user, onHeaderClicked = { onSelected(idC) ; scope.launch {
    snackbarHostState.showSnackbar(message = "segasc", actionLabel = "Cerrar", duration = SnackbarDuration.Short)
  }
  })
}




//////////////////////////////////////////////////////////////////////////////////////

// ASIGNATURA DE CARRERA
@Composable
fun firstLoad22(checked: Boolean,idC: Int): List<AsignaturaRequest>? {
  var listAsignaturas by remember { mutableStateOf<List<AsignaturaRequest>?>(null) }
  LaunchedEffect(checked) {
    UserRepository.loggedInUser()?.let { user ->
      UserRepository.getToken()?.let { token ->
        if (checked) {
          println("idc " + idC)
          getAsignaturasDeCarreraRequest(idC,token) { success ->
            listAsignaturas = success
          }
        }
      }
    }
  }
  return listAsignaturas
}


@Composable
fun AsigaturaDeCarrera(modifier: Modifier, carreraId:Int, onHeaderClicked: (Int) -> Unit) {
  val nombreAsignaturaList = remember { mutableStateListOf<AsignaturaRequest>() }
  val isLoading = remember { mutableStateOf(false) }
  val listState = rememberLazyListState()
  val coroutineScope = rememberCoroutineScope()
  var asignaturas by remember { mutableStateOf<List<AsignaturaRequest>?>(null) }
  var checked by remember { mutableStateOf(true) }

  asignaturas = firstLoad22(checked, carreraId)
  LaunchedEffect(asignaturas) {
    nombreAsignaturaList.clear()
    asignaturas?.let {
      loadMoreAsignatura(nombreAsignaturaList, it)
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
          AsignaturaItem(user = nombreAsignaturaList[index].nombre, idC = nombreAsignaturaList[index].idAsignatura) {
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
    /*  LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }.collect { index ->
          if (index == nombreAsignaturaList.size - 1 && ! isLoading.value && nombreAsignaturaList.size <= asignaturas !!.size) {
            isLoading.value = true
            coroutineScope.launch {
              delay(3000)
              loadMoreAsignatura(nombreAsignaturaList, asignaturas !!)
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


fun loadMoreAsignatura(asignaturasList: MutableList<AsignaturaRequest>, asignaturas: List<AsignaturaRequest>) {
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
fun AsignaturaItem(user: String, idC: Int, onSelected: (Int) -> Unit) {
  CarreraCard(nombre = user, onHeaderClicked = { onSelected(idC) })
}




////////////////////////////////////////////////////////////


// HORARIO ASIGNATURA
@Composable
fun firstLoad32(checked: Boolean,asignaturaId:Int): List<HorariosAsignaturaRequest>? {
  var carreras by remember { mutableStateOf<List<HorariosAsignaturaRequest>?>(null) }
  LaunchedEffect(checked) {
    UserRepository.loggedInUser()?.let { id ->
      UserRepository.getToken()?.let { token ->

        if (checked) {
          println(id)
          getHorariosAsignaturaRequest(asignaturaId,token) { responde ->
            carreras = responde
          }

        }
      }
    }
  }
  return carreras
}


@Composable
fun HorarioAsignatura(modifier: Modifier, asignaturaId:Int, onHeaderClicked: (Int) -> Unit) {
  val nombreCarrerasList = remember { mutableStateListOf<HorariosAsignaturaRequest>() }
  val isLoading = remember { mutableStateOf(false) }
  val listState = rememberLazyListState()
  val coroutineScope = rememberCoroutineScope()
  var carreras by remember { mutableStateOf<List<HorariosAsignaturaRequest>?>(null) }
  var checked by remember { mutableStateOf(true) }

  carreras = firstLoad32(checked,asignaturaId)
  LaunchedEffect(carreras) {
    nombreCarrerasList.clear()
    carreras?.let {
      loadMoreAsigDeCarrera2(nombreCarrerasList, it)
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
      text = stringResource(id = txt_selectHorario),
      style = MaterialTheme.typography.headlineSmall,
    )
    if (carreras != null) {
      LazyColumn(state = listState, modifier = Modifier
        .weight(1f)
        .padding(bottom = 20.dp)) {
        items(nombreCarrerasList.size) { index ->
          AsigDeCarreraItem2(user = nombreCarrerasList[index].dtHorarioDias[index].diaSemana + " de " +
           nombreCarrerasList[index].dtHorarioDias[index].horaInicio + " a " +
           nombreCarrerasList[index].dtHorarioDias[index].horaFin + "hs",
            idC = nombreCarrerasList[index].idHorarioAsignatura) {
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
              loadMoreAsigDeCarrera2(nombreCarrerasList, carreras !!)
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


fun loadMoreAsigDeCarrera2(carrerasList: MutableList<HorariosAsignaturaRequest>, carreras: List<HorariosAsignaturaRequest>) {
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


@Composable
fun AsigDeCarreraItem2(user: String, idC: Int, onSelected: (Int) -> Unit) {
  CarreraCard(nombre = user, onHeaderClicked = { onSelected(idC) })
}