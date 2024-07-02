package com.example.compose.studyhub.ui.screen.estudiante

import CarreraRequest
import InscripcionCarreraRequest
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.studyhub.R
import com.example.compose.studyhub.R.string.txt_inscripciones
import com.example.compose.studyhub.R.string.txt_selectCarrera
import com.example.compose.studyhub.data.UserRepository
import com.example.compose.studyhub.domain.formatDate
import com.example.compose.studyhub.http.requests.getCarrerasRequest
import com.example.compose.studyhub.http.requests.inscripcionCarreraRequest
import com.example.compose.studyhub.ui.component.CarreraCard
import com.example.compose.studyhub.ui.component.ConfirmDialogBox
import com.example.compose.studyhub.ui.component.searchBar.LocalAccountsDataProvider
import com.example.compose.studyhub.ui.component.searchBar.SearchBarScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


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
    val showConfirmationDialog = remember { mutableStateOf(false) }
    val showErrorDialog = remember { mutableStateOf(false) }

    LaunchedEffect(onError){
        if(onError!=null){
            if(onError==""){
                showConfirmationDialog.value = true
            }
            else{
                println("On error: $onError")
                showErrorDialog.value = true
            }
        }
    }

    Column(modifier = Modifier.padding(top = 50.dp, bottom = 1.dp)) {
        if (remIdCarrera.value == null) {
            Carreras(
                modifier = Modifier.fillMaxWidth(),
                snackbarHostState,
                scope,
                onHeaderClicked = { idC: Int? ->
                    if (idC != null) {
                        remIdCarrera.value = idC
                        onInscripcionCarreraSubmitted(remIdCarrera.value!!)
                        println("Este remIdCarrera : ${remIdCarrera.value}")
                    }

                })
        }
        if(showConfirmationDialog.value){
            ConfirmDialogBox(onDismissRequest = { onInscripcionCarreraConfirmed()
            ; showConfirmationDialog.value = false }, dialogTitle = onSuccess?:"")
        }
        if(showErrorDialog.value){
            ConfirmDialogBox(onDismissRequest = {
                showErrorDialog.value = false; remIdCarrera.value = null }, dialogTitle = onError?:"")

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
    val isLoading = remember { mutableStateOf(false) }
    val listState = rememberLazyListState()
    var carreras by remember { mutableStateOf<List<CarreraRequest>?>(null) }
    val checked by remember { mutableStateOf(true) }

    carreras = firstLoad2(checked)
    LaunchedEffect(carreras) {
        nombreCarrerasList.clear()
        carreras?.let {
            loadMoreCarrera(nombreCarrerasList, it)
        }
    }

    Column(
        modifier = modifier.fillMaxWidth()
            .padding(top = 60.dp, bottom = 1.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        SearchBarScreen(
            emails = LocalAccountsDataProvider.allUserContactAccounts,
            modifier = Modifier,
            navigateToDetail = { _, _ -> }
        )
        Text(
            text = stringResource(id = txt_selectCarrera),
            style = MaterialTheme.typography.headlineSmall,
        )
        if (carreras != null) {
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
                        }
                    }
                }
            }
        } else {
            Text(
                text = stringResource(id = R.string.txt_error_solicitudes),
                textAlign = TextAlign.Center
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

@Preview
@Composable
fun InscripcionScreenPreview() { // Carreras(modifier = Modifier.fillMaxWidth(), onHeaderClicked = {})
    InscripcionCarreraScreen(onInscripcionCarreraSubmitted = {}, onInscripcionCarreraConfirmed = {}, onNavUp = {}, onError = "")
}