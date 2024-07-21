package com.example.compose.studyhub.ui.screen.estudiante

import CarreraRequest
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.compose.studyhub.R
import com.example.compose.studyhub.R.string.txt_selectCarrera
import com.example.compose.studyhub.data.UserRepository
import com.example.compose.studyhub.http.requests.getCarrerasRequest
import com.example.compose.studyhub.ui.component.CarreraCard
import com.example.compose.studyhub.ui.component.ConfirmDialogBox
import com.example.compose.studyhub.ui.component.searchBar.SearchListCarreras
import kotlinx.coroutines.CoroutineScope


@Composable
fun InscripcionCarreraScreen(
  onInscripcionCarreraSubmitted: (idCarrera: Int) -> Unit,
  onInscripcionCarreraConfirmed: () -> Unit,
  onNavUp: () -> Unit,
  onError: String? = null,
  onSuccess: String? = null
): DrawerState {
    val remIdCarrera = remember { mutableStateOf<Int?>(null) }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    var respone by remember { mutableStateOf<String?>(null) }
    val showConfirmationDialog = remember { mutableStateOf(false) }
    val showErrorDialog = remember { mutableStateOf(false) }

    LaunchedEffect(onError){
        if(onError!=null){
            if(onError==""){
                showConfirmationDialog.value = true
            }
            else{
                showErrorDialog.value = true
            }
        }
    }

    Column(modifier = Modifier.padding(top = 50.dp, bottom = 1.dp)) {
        if (remIdCarrera.value == null) {
            Carreras(
                modifier = Modifier.fillMaxWidth(),
                snackbarHostState = snackbarHostState,
                scope = scope,
                onHeaderClicked = { idC: Int? ->
                    if (idC != null) {
                        remIdCarrera.value = idC
                        onInscripcionCarreraSubmitted(remIdCarrera.value!!)
                    }
                })
        } else {
            //CarrerasScreen(carreraId = remIdCarrera.value !!)
        }

        if(showConfirmationDialog.value){
            ConfirmDialogBox(onDismissRequest = { onInscripcionCarreraConfirmed()
            ; showConfirmationDialog.value = false }, dialogTitle = onSuccess?:"")
        }
        if(showErrorDialog.value){
            ConfirmDialogBox(onDismissRequest = { onInscripcionCarreraConfirmed()
            ; showErrorDialog.value = false }, dialogTitle = "El estudiante ya tiene una inscripción activa.")
        }
    }
    return DrawerState(DrawerValue.Closed)
}


@Composable
fun firstLoad2(checked: Boolean): List<CarreraRequest>? {
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
fun Carreras(modifier: Modifier, snackbarHostState: SnackbarHostState, scope: CoroutineScope, onHeaderClicked: (Int) -> Unit) {
    val nombreCarrerasList = remember { mutableStateListOf<CarreraRequest>() }
    var carreras by remember { mutableStateOf<List<CarreraRequest>?>(null) }
    var checked by remember { mutableStateOf(true) }
    var selectedItem by remember { mutableStateOf<CarreraRequest?>(null) }

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
            .padding(top = 70.dp, bottom = 1.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(id = txt_selectCarrera),
            style = MaterialTheme.typography.headlineSmall,
        )
      /*  if (carreras != null) {
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = 20.dp)
            ) {
                items(nombreCarrerasList.size) { index ->
                    CarreraItem(
                        user = nombreCarrerasList[index].nombre,
                        snackbarHostState,
                        scope ,
                        idC = nombreCarrerasList[index].idCarrera) {
                        onHeaderClicked(it)
                    }
                }
                if (isLoading.value) {
                    item {
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                        ) {
                            //CircularProgressIndicator()
                        }
                    }
                }
            }*/
        if (carreras != null) {
            SearchListCarreras(
                items = nombreCarrerasList,
                selectedItem = nombreCarrerasList.firstOrNull(),
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 1.dp, bottom = 10.dp),
                onItemSelected = { itemSelected ->
                    selectedItem = itemSelected
                    onHeaderClicked(itemSelected.idCarrera)
                }
            )
            /* LaunchedEffect(listState) {
                 snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }.collect { index ->
                     if (index == nombreCarrerasList.size - 1 && !isLoading.value && nombreCarrerasList.size <= carreras!!.size) {
                         isLoading.value = true
                         coroutineScope.launch {
                             delay(3000)
                             loadMoreCarrera(nombreCarrerasList, carreras!!)
                             isLoading.value = false
                         }
                     }
                 }
             }*/
        } else {
            Text(
                text = stringResource(id = R.string.txt_error_solicitudes),
                modifier = Modifier.padding(top = 15.dp, start = 6.dp),
                style = MaterialTheme.typography.bodyMedium
            )
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
fun CarreraItem(user: String, snackbarHostState: SnackbarHostState, scope: CoroutineScope, idC: Int, onSelected: (Int) -> Unit) {
    CarreraCard(nombre = user, onHeaderClicked = { onSelected(idC) })
}


