package com.example.compose.studyhub.ui.component.inscripciones

import CarreraRequest
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.compose.studyhub.R.string.txt_selectCarrera
import com.example.compose.studyhub.data.UserRepository
import com.example.compose.studyhub.http.requests.inscripcionesCarreraRequest
import com.example.compose.studyhub.ui.component.CarreraCard
import com.example.compose.studyhub.ui.component.searchBar.SearchList
import com.example.compose.studyhub.util.InfiniteScrolling.firstLoad
import com.example.compose.studyhub.util.InfiniteScrolling.loadMoreItems
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CarrerasInscripto(modifier: Modifier,onHeaderClicked: (Int) -> Unit) {
    val nombreCarrerasList = remember { mutableStateListOf<CarreraRequest>() }
    val isLoading = remember { mutableStateOf(false) }
    val listState = rememberLazyListState()
    var carreras by remember { mutableStateOf<List<CarreraRequest>?>(null) }
    val coroutineScope = rememberCoroutineScope()
    var selectedItem by remember { mutableStateOf<CarreraRequest?>(null) }

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
                 .padding(top = 70.dp, bottom = 1.dp),
             verticalArrangement = Arrangement.spacedBy(18.dp),
             horizontalAlignment = Alignment.CenterHorizontally,
         ) {
        Text(
            text = stringResource(id = txt_selectCarrera),
            style = MaterialTheme.typography.headlineSmall,
        )
        if (carreras != null) {

            SearchList(
                items = nombreCarrerasList,
                selectedItem = selectedItem,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 1.dp, bottom = 10.dp),
                onItemSelected = { itemSelected ->
                   selectedItem = itemSelected
                    onHeaderClicked(itemSelected.idCarrera)
                }
            )

    /*    if (carreras != null) {
          LazyColumn(state = listState, modifier = Modifier
                .weight(1f)
                .padding(top = 20.dp)) {
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
            Text(text = stringResource(id = R.string.txt_error_solicitudes), textAlign = TextAlign.Center)*/
      }
    }

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }.collect { index ->
            if (index == nombreCarrerasList.size - 1 && !isLoading.value && nombreCarrerasList.size <= (carreras?.size ?: 0)
            ) {
                isLoading.value = true
                coroutineScope.launch {
                    delay(3000)
                    loadMoreItems(nombreCarrerasList, carreras!!)
                    isLoading.value = false
                }
            }
        }
    }
}


@Composable
fun CarreraItem(user: String, idC: Int, onSelected: (Int) -> Unit) {
    CarreraCard(nombre = user, onHeaderClicked = { onSelected(idC) })
}
