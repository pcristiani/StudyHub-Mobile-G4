package com.example.compose.studyhub.ui.estudiante

import AsignaturaRequest
import CarreraRequest
import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.compose.studyhub.R
import com.example.compose.studyhub.data.UserRepository
import com.example.compose.studyhub.http.requests.getCarrerasRequest
import com.example.compose.studyhub.ui.component.CarreraCard
import com.example.compose.studyhub.ui.theme.slightlyDeemphasizedAlpha
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.html.Entities
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody.Companion.toResponseBody
import kotlin.collections.indexOf

@Composable
fun InscripcionScreen(): DrawerState {
  Column(modifier = Modifier.padding(top = 60.dp, bottom = 1.dp)) {
    Carreras(modifier = Modifier.fillMaxWidth())
  }

  return DrawerState(DrawerValue.Closed)
}
@Composable
fun Carreras(modifier: Modifier) {
  val asignaturasList = remember { mutableStateListOf<String>() }
  val isLoading = remember { mutableStateOf(false) }
  val listState = rememberLazyListState()
  val coroutineScope = rememberCoroutineScope()
  var asignaturas by remember { mutableStateOf<List<CarreraRequest>?>(null) }
  var checked by remember { mutableStateOf(true) }


  asignaturas = firstLoad2(checked)
  LaunchedEffect(asignaturas) {
    asignaturasList.clear()
    asignaturas?.let {
      loadMoreAsignaturas2(asignaturasList, it)
    }
  }

  Column(
    modifier = modifier.fillMaxWidth(),
    verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.Top),
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    Text(
      text = stringResource(id = R.string.txt_inscripciones),
      style = MaterialTheme.typography.headlineLarge,
    )/*     Row(modifier = Modifier
      .fillMaxWidth()
      .padding(start = 10.dp)) {
      Switch(colors = SwitchDefaults.colors(), checked = checked, onCheckedChange = { newChecked ->
        checked = newChecked
      })
    } */

    if (asignaturas !== null) {
      LazyColumn(state = listState, modifier = Modifier
        .weight(1f)
        .padding(bottom = 20.dp)) {
        items(asignaturasList.size) { index ->
          UserItem2(user = asignaturasList[index])
        }/*     if (isLoading.value) {
          item {
            Box(modifier = Modifier
              .fillMaxWidth()
              .padding(16.dp)) { *//* CircularProgressIndicator(
                        modifier = Modifier
                           .padding(16.dp)
                           .align(Alignment.Center)
                                              ) *//*
            }
          }
        } */
      }

      LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }.collect { index ->
          if (index == asignaturasList.size - 1 && ! isLoading.value && asignaturasList.size <= asignaturas !!.size) {
            isLoading.value = true
            coroutineScope.launch {
              delay(3000) // Simulate network delay
              loadMoreAsignaturas2(asignaturasList, asignaturas !!)
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
@Composable
fun firstLoad2(checked: Boolean): List<CarreraRequest>? {
  var carreras by remember { mutableStateOf<List<CarreraRequest>?>(null) }
  LaunchedEffect(checked) {
    UserRepository.getToken()?.let { token ->
      getCarrerasRequest(token) { carreras ->
        carreras?.let {
          println("Carreras: " + carreras)

          println("Carreras: $it")
        } ?: run {
          println("Error al obtener las carreras.")
        }
      }
    }
  }
  return carreras
}

suspend fun loadMoreAsignaturas2(asignaturasList: MutableList<String>, asignaturas: List<CarreraRequest>) {
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
@Composable
fun UserItem2(user: String) {
  CarreraCard(
    nombre = user,
  )
}
@Preview(name = "Light", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun InscripcionScreenPreview() {
  InscripcionScreen() // SelectAvatarUsuarioPreview2() // SelectCarreras(titleResourceId = R.string.txt_question, directionsResourceId = R.string.select_rol, possibleAnswers = listOf(R.string.txt_estudiante,R.string.txt_estudiante,R.string.txt_admin), selectedAnswers = listOf(R.string.txt_estudiante,R.string.txt_admin), onOptionSelected = { _, _ -> })
} //
//
// data class Avatar2(@StringRes val stringResourceId: Int,  @DrawableRes  val imageResourceId: Int)
/*

@Composable
fun SelectAvatarUsuario2(
  @StringRes titleResourceId: Int,
  possibleAnswers: List<Avatar2>,
  selectedAnswer: Avatar2?,
  onOptionSelected: (Avatar2) -> Unit,
  modifier: Modifier = Modifier,
) {
/*   Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) { *//*       Text(text = "Home Screen")
      Button(onClick = { *//*  *//*         val navController = rememberNavController() *//*  *//*      navController.navigate("detail") *//*  *//*   }) {
         Text(text = "Go to Detail Screen")
      } *//*
  } */
  QuestionWrapper(
    titleResourceId = titleResourceId,
    modifier = modifier.selectableGroup(),
  ) {
    possibleAnswers.forEach {
      val selected = it == selectedAnswer
      RadioButtonWithImageRow2(modifier = Modifier.padding(vertical = 4.dp), text = stringResource(id = it.stringResourceId), imageResourceId = it.imageResourceId, selected = selected, onOptionSelected = { onOptionSelected(it) })
    }
  }
}


@Composable
fun RadioButtonWithImageRow2(
  text: String,
  @DrawableRes imageResourceId: Int,
  selected: Boolean,
  onOptionSelected: () -> Unit,
  modifier: Modifier = Modifier,
) {
  Surface(shape = MaterialTheme.shapes.small, color = if (selected) {
    MaterialTheme.colorScheme.primaryContainer
  } else {
    MaterialTheme.colorScheme.surface
  }, border = BorderStroke(width = 1.dp, color = if (selected) {
    MaterialTheme.colorScheme.primary
  } else {
    MaterialTheme.colorScheme.outline
  }), modifier = modifier
    .clip(MaterialTheme.shapes.small)
    .selectable(selected, onClick = onOptionSelected, role = Role.RadioButton)) {
    Row(modifier = Modifier
      .fillMaxWidth()
      .padding(6.dp), verticalAlignment = Alignment.CenterVertically) {
      Image(painter = painterResource(id = imageResourceId), contentDescription = null, modifier = Modifier
        .size(40.dp)
        .clip(MaterialTheme.shapes.extraSmall)
        .padding(start = 2.dp, end = 2.dp))
      Spacer(Modifier.width(10.dp))

      Text(text, Modifier.weight(0.1f), style = MaterialTheme.typography.bodyLarge)
      Box(Modifier.padding(10.dp)) { RadioButton(selected, onClick = null) }
    }
  }
}




*//* @Preview *//*

@Composable
fun SelectAvatarUsuarioPreview2(nombre: String) {
  val sas=R.string.asignatura1;
  var asignaturas by remember { mutableStateOf<List<AsignaturaRequest>?>(null) }

  LaunchedEffect(true) {
    UserRepository.loggedInUser()?.let { user ->
      UserRepository.getToken()?.let { token ->
        getAsignaturasNoAprobadasRequest(user, token) { success ->
          asignaturas = success
          println("qAsignaturass: " + success)
        }
      }
    }
  }

  val possibleAnswers = listOf(
    Avatar2(sas, R.drawable.book),
    Avatar2(R.string.asignatura2, R.drawable.aprendeonline),
    Avatar2(R.string.asignatura3, R.drawable.lectura),
    Avatar2(R.string.asignatura4, R.drawable.booknote),
    Avatar2(R.string.asignatura5, R.drawable.educacion),
  )
  var selectedAnswer by remember { mutableStateOf<Avatar2?>(null) }
  SelectAvatarUsuario2(
    titleResourceId = R.string.seleccionar_carrera,
    possibleAnswers = possibleAnswers,
    selectedAnswer = selectedAnswer,
    onOptionSelected = { selectedAnswer = it },
  )
}*/
