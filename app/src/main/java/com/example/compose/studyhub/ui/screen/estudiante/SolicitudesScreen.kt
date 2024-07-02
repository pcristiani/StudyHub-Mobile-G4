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
import com.example.compose.studyhub.ui.component.DatosAsignaturaBox
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
  val asignaturasList = remember { mutableStateListOf<AsignaturaRequest>() }
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
  var showAsignaturaInfo by remember { mutableStateOf(false) }
  var idAsignaturaSelected by remember { mutableIntStateOf(0)}

  //Carga todas las asignaturas a las que el usuario está registrado
  allAsignaturas = firstLoad(checked)

  //Genera una lista (asignaturas) a partir de allAsignaturas con únicamente las que pertenecen a la carrera seleccionada en carreraSelected
  LaunchedEffect(carreraSelected, checked, allAsignaturas){
    asignaturas = emptyList()
    allAsignaturas?.forEach{
      if(it.idCarrera == carreraSelected?.idCarrera){
        asignaturas += it

      }
    }
  }

  //Cuando la lista de asignaturas cargadas se actualiza (debido a que el usuario cambió la selección de carrera), se limpia la lista en pantalla y se carga de nuevo
  LaunchedEffect(asignaturas) {
    asignaturasList.clear()
    loadMoreAsignaturas(asignaturasList, asignaturas)
  }

  //Retornar todas las carreras a las que el usuario está inscripto en las listas listaCarreras, nombresCarrera e idsCarrera
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

    //Lista expandible con todas las carreras a las que el usuario está inscripto
    ExpandableList(modifier= Modifier
      .padding(top = 0.dp, bottom = 10.dp, start = 20.dp, end = 20.dp)
      .animateContentSize(),
      headerTitle = carreraSelected?.nombre ?: stringResource(id = R.string.txt_selectCarrera), options = nombresCarrera, optionIds = idsCarrera, onOptionSelected={ selectedId -> carreraSelected =
        listaCarreras?.find {it.idCarrera ==selectedId }
      })

    //Todo el resto de la screen carga únicamente si el usuario seleccionó una carrera
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

      //Si el usuario está isncripto a alguna asignatura de la carrera que eligió, se muestra en pantalla una lista con infinite scrolling de éstas
      if (asignaturas.isNotEmpty()) {
        LazyColumn(state = listState, modifier = Modifier
          .weight(1f)
          .padding(bottom = 20.dp)) {
          items(asignaturasList.size) { index ->
            ListItem(nombre = asignaturasList[index].nombre, idAsignatura = asignaturasList[index].idAsignatura, onClick = {idAsignatura -> idAsignaturaSelected = idAsignatura; showAsignaturaInfo = true})
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
        //Cuando la lista de infiniteScrolling se mueve hasta el final
        LaunchedEffect(listState) {
          snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }.collect { index ->
            //Y si todavía quedan asignaturas en la lista de asignaturas que no se muestran en pantalla
            if (index == asignaturasList.size - 1 && ! isLoading.value && asignaturasList.size <= asignaturas.size) {
              isLoading.value = true
              //Se cargan más asignaturas en pantalla
              coroutineScope.launch {
                delay(3000)
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

    //Si el usuario pincha en una asignatura, se despliega una box con los datos de ésta
    if(showAsignaturaInfo){
      val asignatura: AsignaturaRequest? = asignaturasList.find{it.idAsignatura == idAsignaturaSelected}

      DatosAsignaturaBox(
        onDismissRequest = {showAsignaturaInfo=false},
        asignatura = asignatura!!.nombre,
        carrera = carreraSelected!!.nombre,
        descripcion = asignatura.descripcion,
        departamento = asignatura.departamento,
        creditos = asignatura.creditos
      )
    }
  }

}

//Función de primera carga
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

//Se ejecuta cuando se quieren cargar más asignaturas a la lista que se está mostrando en pantalla
fun loadMoreAsignaturas(asignaturasList: MutableList<AsignaturaRequest>, asignaturas: List<AsignaturaRequest>) {
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

//Cómo se va a mostrar cada item de la lista
@Composable
fun ListItem(nombre: String, idAsignatura: Int, onClick: (Int) -> Unit) {
  AsignaturaCard(
    nombre = nombre,
    onClick = { onClick(idAsignatura) }
  )
}

@Preview
@Composable
fun SolicitudesScreenPreview() {
  ThemeStudyHub {
    SolicitudesScreen()
  }
}