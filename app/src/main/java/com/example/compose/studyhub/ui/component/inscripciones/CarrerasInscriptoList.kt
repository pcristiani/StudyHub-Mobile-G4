package com.example.compose.studyhub.ui.component.inscripciones

import CarreraRequest
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
import com.example.compose.studyhub.R.string.txt_selectCarrera
import com.example.compose.studyhub.data.Account
import com.example.compose.studyhub.data.UserRepository
import com.example.compose.studyhub.http.requests.inscripcionesCarreraRequest

import com.example.compose.studyhub.ui.component.CarreraCard
import com.example.compose.studyhub.ui.component.searchBar.LocalAccountsDataProvider
import com.example.compose.studyhub.ui.component.searchBar.SearchBarScreen
import com.example.compose.studyhub.util.InfiniteScrolling.firstLoad
import com.example.compose.studyhub.util.InfiniteScrolling.loadMoreItems
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CarrerasInscripto(modifier: Modifier, onHeaderClicked: (Int) -> Unit) {
    val nombreCarrerasList = remember { mutableStateListOf<CarreraRequest>() }
    val isLoading = remember { mutableStateOf(false) }
    val listState = rememberLazyListState()
    var carreras by remember { mutableStateOf<List<CarreraRequest>?>(null) }
    val coroutineScope = rememberCoroutineScope()

    carreras = firstLoad(UserRepository.loggedInUser()?:0,::inscripcionesCarreraRequest)
    LaunchedEffect(carreras) {
        nombreCarrerasList.clear()
        carreras?.let {
            nombreCarrerasList.clear()
            loadMoreItems(nombreCarrerasList, it)
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
            emails = datosCarreras.allUserAccountss,
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
                    CarreraItem(user = nombreCarrerasList[index].nombre, idC = nombreCarrerasList[index].idCarrera) {
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
        } else {
            Text(text = stringResource(id = R.string.txt_error_solicitudes), textAlign = TextAlign.Center)
        }
    }

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }.collect { index ->
            //Y si todavía quedan asignaturas en la lista de asignaturas que no se muestran en pantalla
            if (index == nombreCarrerasList.size - 1 && !isLoading.value && nombreCarrerasList.size <= (carreras?.size ?: 0)
            ) {
                isLoading.value = true
                //Se cargan más asignaturas en pantalla
                coroutineScope.launch {
                    delay(3000)
                    loadMoreItems(nombreCarrerasList, carreras!!)
                    isLoading.value = false
                }
            }
        }
    }
}

object datosCarreras {

    val allUserAccountss = listOf(

        Account(
            idCarrera = 1, nombre = "aTecnologo Mecanica", descripcion = "Carrera terciaria de mecánica."
        ),
        Account(
            idCarrera = 2, nombre = "aTecnologo Biologia", descripcion = "Carrera terciaria de biología."
        ),
        Account(
            idCarrera = 3, nombre = "Tecnologo Informatica", descripcion = "Carrera terciaria de informática."
        ),
        Account(
            idCarrera = 4, nombre = "Tecnologo Quimica", descripcion = "Carrera terciaria de química."
        ),
    )
}

@Composable
fun CarreraItem(user: String, idC: Int, onSelected: (Int) -> Unit) {
    CarreraCard(nombre = user, onHeaderClicked = { onSelected(idC) })
}
