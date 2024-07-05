package com.example.compose.studyhub.ui.component.inscripciones

import HorariosAsignaturaRequest
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.compose.studyhub.R
import com.example.compose.studyhub.R.string.txt_selectHorario
import com.example.compose.studyhub.data.UserRepository
import com.example.compose.studyhub.http.requests.getHorariosAsignaturaRequest
import com.example.compose.studyhub.ui.component.CarreraCard
import com.example.compose.studyhub.ui.screen.estudiante.loadMoreAsignaturas
import com.example.compose.studyhub.util.InfiniteScrolling.firstLoad
import com.example.compose.studyhub.util.InfiniteScrolling.loadMoreItems
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun HorariosAsignatura(modifier: Modifier, asignaturaId:Int, onHeaderClicked: (Int) -> Unit) {
    val horariosAsignaturaList = remember { mutableStateListOf<HorariosAsignaturaRequest>() }
    val isLoading = remember { mutableStateOf(false) }
    val listState = rememberLazyListState()
    var horariosAsignatura by remember { mutableStateOf<List<HorariosAsignaturaRequest>?>(null) }
    val coroutineScope = rememberCoroutineScope()

    horariosAsignatura = firstLoad(asignaturaId, ::getHorariosAsignaturaRequest)
    LaunchedEffect(horariosAsignatura) {
        if(horariosAsignatura!=null){
            horariosAsignaturaList.clear()
            horariosAsignatura?.let {
                loadMoreItems(horariosAsignaturaList, it)
            }
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
            text = stringResource(id = txt_selectHorario),
            style = MaterialTheme.typography.headlineSmall,
        )
        if (horariosAsignatura != null) {
            println("I'm here")
            LazyColumn(state = listState, modifier = Modifier
                .weight(1f)
                .padding(bottom = 20.dp)) {
                items(horariosAsignaturaList.size) { index ->
                    val horariosAsignaturaItem = horariosAsignaturaList[index]
                    horariosAsignaturaItem.dtHorarioDias.forEach { horarioDia ->
                        HorariosAsignaturaItem(
                            user = "${horarioDia.diaSemana} de ${horarioDia.horaInicio} a ${horarioDia.horaFin}hs",
                            idC = horariosAsignaturaItem.idHorarioAsignatura
                        ) {
                            onHeaderClicked(it)
                        }
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
            Text(text = stringResource(id = R.string.txt_error_horario), textAlign = TextAlign.Center)
        }
    }
    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }.collect { index ->
            //Y si todavía quedan asignaturas en la lista de asignaturas que no se muestran en pantalla
            if (index == horariosAsignaturaList.size - 1 && !isLoading.value && horariosAsignaturaList.size <= (horariosAsignatura?.size ?: 0)
            ) {
                isLoading.value = true
                //Se cargan más asignaturas en pantalla
                coroutineScope.launch {
                    delay(3000)
                    loadMoreItems(horariosAsignaturaList, horariosAsignatura!!)
                    isLoading.value = false
                }
            }
        }
    }
}

@Composable
fun HorariosAsignaturaItem(user: String, idC: Int, onSelected: (Int) -> Unit) {
    CarreraCard(nombre = user, onHeaderClicked = { onSelected(idC) })
}