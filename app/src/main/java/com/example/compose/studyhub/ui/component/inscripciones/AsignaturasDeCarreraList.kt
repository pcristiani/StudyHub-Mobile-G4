package com.example.compose.studyhub.ui.component.inscripciones

import AsignaturaRequest
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
import com.example.compose.studyhub.R.string.txt_selectAsignatura
import com.example.compose.studyhub.R.string.txt_selectCarrera
import com.example.compose.studyhub.data.UserRepository
import com.example.compose.studyhub.http.requests.getAsignaturasDeCarreraRequest
import com.example.compose.studyhub.ui.component.CarreraCard
import com.example.compose.studyhub.ui.component.searchBar.SearchList
import com.example.compose.studyhub.ui.component.searchBar.SearchListAsignaturas
import com.example.compose.studyhub.util.InfiniteScrolling.firstLoad
import com.example.compose.studyhub.util.InfiniteScrolling.loadMoreItems
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun AsigaturaDeCarrera(modifier: Modifier, carreraId:Int, onHeaderClicked: (Int) -> Unit) {
    val nombreAsignaturaList = remember { mutableStateListOf<AsignaturaRequest>() }
    val isLoading = remember { mutableStateOf(false) }
    val listState = rememberLazyListState()
    var asignaturas by remember { mutableStateOf<List<AsignaturaRequest>?>(null) }
    val coroutineScope = rememberCoroutineScope()
    var selectedItem by remember { mutableStateOf<AsignaturaRequest?>(null) }

    asignaturas = firstLoad(carreraId, ::getAsignaturasDeCarreraRequest)
    LaunchedEffect(asignaturas) {
        nombreAsignaturaList.clear()
        asignaturas?.let {
            loadMoreItems(nombreAsignaturaList, it)
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 70.dp, bottom = 1.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(id = txt_selectAsignatura),
            style = MaterialTheme.typography.headlineSmall,
        )
/*
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

            } else {
            Text(text = stringResource(id = R.string.txt_error_solicitudes), textAlign = TextAlign.Center)
        }*/
            if (asignaturas != null) {
                SearchListAsignaturas(
                    items = nombreAsignaturaList,
                    selectedItem = selectedItem,
                    carreraId=carreraId,
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(top = 1.dp, bottom = 10.dp),
                    onItemSelected = { itemSelected ->
                        selectedItem = itemSelected
                        onHeaderClicked(itemSelected.idAsignatura)
                        println("Este es el item Selected: $itemSelected")
                    }
                )
            }
         /*   else {
                Text(text = stringResource(id = R.string.txt_error_solicitudes), textAlign = TextAlign.Center)
            }*/
    }
    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }.collect { index ->
            //Y si todavía quedan asignaturas en la lista de asignaturas que no se muestran en pantalla
            if (index == nombreAsignaturaList.size - 1 && !isLoading.value && nombreAsignaturaList.size <= (asignaturas?.size ?: 0)
            ) {
                isLoading.value = true
                //Se cargan más asignaturas en pantalla
                coroutineScope.launch {
                    delay(3000)
                    loadMoreItems(nombreAsignaturaList, asignaturas!!)
                    isLoading.value = false
                }
            }
        }
    }
}

@Composable
fun AsignaturaItem(user: String, idC: Int, onSelected: (Int) -> Unit) {
    CarreraCard(nombre = user, onHeaderClicked = { onSelected(idC) })
}

