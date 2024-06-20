package com.example.compose.studyhub.ui.estudiante

import AsignaturaRequest
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
import com.example.compose.studyhub.R
import com.example.compose.studyhub.data.UserRepository
import com.example.compose.studyhub.http.requests.getAsignaturasAprobadasRequest
import com.example.compose.studyhub.http.requests.getAsignaturasNoAprobadasRequest
import com.example.compose.studyhub.ui.component.AsignaturaCard
import com.example.compose.studyhub.ui.theme.ThemeStudyHub
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SolicitudesScreen(): DrawerState {
  Column(modifier = Modifier.padding(top = 120.dp, bottom = 6.dp)) {
    Solicitudes(modifier = Modifier
       .weight(1f)
       .padding(top = 20.dp, start = 20.dp, end = 20.dp))
  }
  return DrawerState(DrawerValue.Closed)
}
@Composable
fun Solicitudes(modifier: Modifier) {
  val asignaturasList = remember { mutableStateListOf<String>() }
  val isLoading = remember { mutableStateOf(false) }
  val listState = rememberLazyListState()
  val coroutineScope = rememberCoroutineScope()
  var asignaturas by remember { mutableStateOf<List<AsignaturaRequest>?>(null) }
  var checked by remember { mutableStateOf(true) }

  asignaturas = firstLoad(checked)
  LaunchedEffect(asignaturas) {
    asignaturasList.clear()
    asignaturas?.let {
      loadMoreAsignaturas(asignaturasList, it)
    }
  }

  Column(
    modifier = modifier.fillMaxWidth(),
    verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.Top),
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    Text(
      text = stringResource(id = R.string.txt_solicitudes),
      style = MaterialTheme.typography.headlineSmall,
    )

    Row(modifier = Modifier
       .fillMaxWidth()
       .padding(start = 10.dp)) {
      Box(modifier = Modifier
         .size(50.dp, 60.dp)
         .padding(bottom = 10.dp, start = 0.dp)) {
        IconButton(onClick = {}, modifier = Modifier.align(Alignment.CenterStart)) {
          Icon(imageVector = Icons.Filled.CalendarMonth, contentDescription = "Calendar")
        }
      }
      Spacer(modifier = Modifier.padding(start = 10.dp))

      Text(text = "Pendientes", modifier = Modifier.padding(top = 15.dp), style = MaterialTheme.typography.bodySmall)
      Spacer(modifier = Modifier.padding(start = 8.dp))
      Switch(colors = SwitchDefaults.colors(), checked = checked, onCheckedChange = { newChecked ->
        checked = newChecked
      })
      Text(text = "Aprobadas", modifier = Modifier.padding(top = 15.dp, start = 6.dp), style = MaterialTheme.typography.bodySmall)
      Spacer(modifier = Modifier.padding(start = 10.dp))

      Box(modifier = Modifier
         .size(50.dp, 60.dp)
         .padding(bottom = 10.dp)) {
        IconButton(onClick = {}, modifier = Modifier.align(Alignment.CenterEnd)) {
          Icon(imageVector = Icons.Filled.Search, contentDescription = "Search")
        }
      }
    }

    if (asignaturas != null) {
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
          if (index == asignaturasList.size - 1 && ! isLoading.value && asignaturasList.size <= asignaturas !!.size) {
            isLoading.value = true
            coroutineScope.launch {
              delay(3000) // Simulate network delay
              loadMoreAsignaturas(asignaturasList, asignaturas !!)
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

suspend fun loadMoreAsignaturas(asignaturasList: MutableList<String>, asignaturas: List<AsignaturaRequest>) {
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
  )
}
