package com.example.compose.studyhub.ui.screen.estudiante

import CarreraRequest
import InscripcionCarreraRequest
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.studyhub.R
import com.example.compose.studyhub.R.string.txt_inscripciones
import com.example.compose.studyhub.R.string.txt_selectCarrera
import com.example.compose.studyhub.data.UserRepository
import com.example.compose.studyhub.http.requests.getCarrerasRequest
import com.example.compose.studyhub.http.requests.inscripcionCarreraRequest
import com.example.compose.studyhub.ui.component.CarreraCard
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun InscripcionCarreraScreen(): DrawerState {
  val remIdCarrera = remember { mutableStateOf<Int?>(null) }

  Column(modifier = Modifier.padding(top = 50.dp, bottom = 1.dp)) {
    if (remIdCarrera.value == null) {
       Carreras(modifier = Modifier.fillMaxWidth(), onHeaderClicked = { idC: Int? ->
        if (idC != null) {
          remIdCarrera.value = idC
          println("Este remIdCarrera : ${remIdCarrera.value}")
        }
      })
    } else {
      CarrerasScreen(carreraId = remIdCarrera.value !!)
    }
  }
  return DrawerState(DrawerValue.Closed)
}


@Composable
fun CarrerasScreen(carreraId: Int) {
  val coroutineScope = rememberCoroutineScope()
  var checked by remember { mutableStateOf(true) }

  LaunchedEffect(carreraId) {
    coroutineScope.launch {
      UserRepository.loggedInUser()?.let { id ->
        UserRepository.getToken()?.let { token ->
          if (checked) {
            inscripcionCarreraRequest(token, InscripcionCarreraRequest(carreraId, id, true)) { success, responde ->
              println("responde: ${id}")
              println("responde:  ${UserRepository.getCI()}")
            }
          }
        }
      }
    }
  }
}


@Composable
fun firstLoad2(checked: Boolean): List<CarreraRequest>? {
  print("ACA ENTRAchecked: $checked")
  var carreras by remember { mutableStateOf<List<CarreraRequest>?>(null) }
  LaunchedEffect(checked) {
    UserRepository.loggedInUser()?.let { user ->
      UserRepository.getToken()?.let { token ->
        if (checked) {
          getCarrerasRequest(token) { success ->
            carreras = success
          }
        }
      }
    }
  }
  return carreras
}

@Composable
fun Carreras(modifier: Modifier, onHeaderClicked: (Int) -> Unit) {
  val nombreCarrerasList = remember { mutableStateListOf<CarreraRequest>() }
  val isLoading = remember { mutableStateOf(false) }
  val listState = rememberLazyListState()
  val coroutineScope = rememberCoroutineScope()
  var carreras by remember { mutableStateOf<List<CarreraRequest>?>(null) }
  var checked by remember { mutableStateOf(true) }

  carreras = firstLoad2(checked)
  LaunchedEffect(carreras) {
    nombreCarrerasList.clear()
    carreras?.let {
      loadMoreCarrera(nombreCarrerasList, it)
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
      text = stringResource(id = txt_selectCarrera),
      style = MaterialTheme.typography.headlineSmall,
    )

 /*  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(top = 60.dp, bottom = 1.dp),
    verticalArrangement = Arrangement.spacedBy(20.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    Text(
      text = stringResource(id = txt_inscripciones),
      style = MaterialTheme.typography.headlineLarge,
    ) */

    if (carreras != null) {
      LazyColumn(state = listState, modifier = Modifier
        .weight(1f)
        .padding(bottom = 20.dp)) {
        items(nombreCarrerasList.size) { index ->
          CarreraItem(user = nombreCarrerasList[index].nombre, idC = nombreCarrerasList[index].idCarrera) {
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
      LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }.collect { index ->
          if (index == nombreCarrerasList.size - 1 && ! isLoading.value && nombreCarrerasList.size <= carreras !!.size) {
            isLoading.value = true
            coroutineScope.launch {
              delay(3000)
              loadMoreCarrera(nombreCarrerasList, carreras !!)
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

fun loadMoreCarrera(carrerasList: MutableList<CarreraRequest>, carreras: List<CarreraRequest>) {
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
fun CarreraItem(user: String, idC: Int, onSelected: (Int) -> Unit) {
  CarreraCard(nombre = user, onHeaderClicked = { onSelected(idC) })
}

@Preview
@Composable
fun InscripcionScreenPreview() { // Carreras(modifier = Modifier.fillMaxWidth(), onHeaderClicked = {})
  InscripcionCarreraScreen()
}

