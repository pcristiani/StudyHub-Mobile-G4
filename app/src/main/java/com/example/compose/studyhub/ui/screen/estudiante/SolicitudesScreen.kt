package com.example.compose.studyhub.ui.screen.estudiante

import AsignaturaRequest
import CarreraRequest
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.compose.studyhub.R
import com.example.compose.studyhub.data.UserRepository
import com.example.compose.studyhub.http.requests.getAsignaturasAprobadasRequest
import com.example.compose.studyhub.http.requests.getAsignaturasNoAprobadasRequest
import com.example.compose.studyhub.http.requests.inscripcionesCarreraRequest
import com.example.compose.studyhub.ui.component.AsignaturaCard
import com.example.compose.studyhub.ui.component.gestion.ExpandableList
import com.example.compose.studyhub.ui.theme.ThemeStudyHub
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SolicitudesScreen(): DrawerState {
  Column(modifier = Modifier.padding(top = 110.dp, bottom = 1.dp)) {
    Solicitudes(modifier = Modifier.fillMaxWidth())
  }
  return DrawerState(DrawerValue.Closed)
}

@Composable
fun Solicitudes(modifier: Modifier) {
  val asignaturasList = remember { mutableStateListOf<String>() }
  val isLoading = remember { mutableStateOf(false) }
  val listState = rememberLazyListState()
  val coroutineScope = rememberCoroutineScope()
  var allAsignaturas by remember { mutableStateOf<List<AsignaturaRequest>?>(null) }
  var asignaturas by remember { mutableStateOf<List<AsignaturaRequest>>(emptyList()) }
  var checked by remember { mutableStateOf(true) }
  var listaCarreras: List<CarreraRequest>? = null
  val nombresCarrera = remember { mutableStateListOf<String>() }
  val idsCarrera = remember { mutableStateListOf<Int>() }
  var carreraSelected by remember { mutableStateOf<CarreraRequest?>(null) }

  allAsignaturas = firstLoad(checked)

  LaunchedEffect(carreraSelected, checked, allAsignaturas){
    asignaturas = emptyList()
    allAsignaturas?.forEach{
      if(it.idCarrera == carreraSelected?.idCarrera){
        asignaturas += it

      }
    }
  }

  LaunchedEffect(asignaturas) {
    asignaturasList.clear()
    loadMoreAsignaturas(asignaturasList, asignaturas)
  }

  UserRepository.loggedInUser()?.let {idUsuario -> UserRepository.getToken()
    ?.let {token -> inscripcionesCarreraRequest(idUsuario, token){success->
      if(success!=null){
        listaCarreras = success
        println(success)
        nombresCarrera.clear()
        idsCarrera.clear()
        listaCarreras?.forEach {
          nombresCarrera.add(it.nombre)
          idsCarrera.add(it.idCarrera)
          println(listaCarreras)
        }
      }
    }}
  }

  Column(
    modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text(
      text = stringResource(id = R.string.txt_solicitudes),
      style = MaterialTheme.typography.headlineLarge,
    )
    Box(modifier = Modifier.padding(20.dp)) {
    }

    ExpandableList(modifier= Modifier
      .padding(top = 0.dp, bottom = 10.dp, start = 20.dp, end = 20.dp)
      .animateContentSize(),
      headerTitle = carreraSelected?.nombre ?: stringResource(id = R.string.txt_selectCarrera), options = nombresCarrera, optionIds = idsCarrera, onOptionSelected={ selectedId -> carreraSelected =
        listaCarreras?.find {it.idCarrera ==selectedId }
      })

    if(carreraSelected!=null){
      Row(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 0.dp)
        .zIndex(0f),
        horizontalArrangement = Arrangement.Center) {

        Text(
          text = "Pendientes",
          modifier = Modifier.padding(top = 15.dp),
          style = MaterialTheme.typography.bodySmall
        )
        Spacer(modifier = Modifier.padding(start = 8.dp))
        Switch(colors = SwitchDefaults.colors(), checked = checked, onCheckedChange = { newChecked -> checked = newChecked
        })
        Text(
          text = "Aprobadas",
          modifier = Modifier.padding(top = 15.dp, start = 6.dp),
          style = MaterialTheme.typography.bodySmall
        )
      }

      if (asignaturas.isNotEmpty()) {
        LazyColumn(state = listState, modifier = Modifier
          .weight(1f)
          .padding(bottom = 20.dp)) {
          items(asignaturasList.size) { index ->
            UserItem(user = asignaturasList[index])
          }
          if (isLoading.value) {
            item {
              Box(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)) {/* CircularProgressIndicator(
                        modifier = Modifier
                           .padding(16.dp)
                           .align(Alignment.Center)
                                              ) */
              }
            }
          }
        }
        LaunchedEffect(listState) {
          snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }.collect { index ->
            if (index == asignaturasList.size - 1 && ! isLoading.value && asignaturasList.size <= asignaturas.size) {
              isLoading.value = true
              coroutineScope.launch {
                delay(3000) // Simulate network delay
                loadMoreAsignaturas(asignaturasList, asignaturas)
                isLoading.value = false
              }
            }
          }
        }
      } else {
        Text(text = stringResource(id = R.string.txt_error_solicitudes), textAlign = TextAlign.Center)
      }
    }

  }
}

@Composable
fun firstLoad(checked: Boolean): List<AsignaturaRequest>? {
  var asignaturas by remember { mutableStateOf<List<AsignaturaRequest>?>(null) }

  LaunchedEffect(checked) {
    UserRepository.loggedInUser()?.let { user ->
      UserRepository.getToken()?.let { token ->
        if (checked) {
          getAsignaturasAprobadasRequest(user, token) { success ->
            asignaturas = success
          }
        } else {
          getAsignaturasNoAprobadasRequest(user, token) { success ->
            asignaturas = success
          }
        }
      }
    }
  }
  return asignaturas
}


fun loadMoreAsignaturas(asignaturasList: MutableList<String>, asignaturas: List<AsignaturaRequest>) {
  val currentSize = asignaturasList.size
  val listLength = if ((asignaturas.size - currentSize) < 30) {
    (asignaturas.size - currentSize)
  } else {
    30
  }
  for (i in 0 until listLength) {
    asignaturasList.add(asignaturas[currentSize + i].nombre)
  }
}

@Preview
@Composable
fun SolicitudesScreenPreview() {
  ThemeStudyHub {
    SolicitudesScreen()
  }
}

@Composable
fun UserItem(user: String) {
  AsignaturaCard(
    nombre = user,
    onClick = {}
  )
}
