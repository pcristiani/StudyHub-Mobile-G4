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
import androidx.compose.runtime.setValue
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
import com.example.compose.studyhub.util.InfiniteScrolling.firstLoad
import com.example.compose.studyhub.util.InfiniteScrolling.loadMoreItems


@Composable
fun HorariosAsignatura(modifier: Modifier, asignaturaId:Int, onHeaderClicked: (Int) -> Unit) {
    val nombreCarrerasList = remember { mutableStateListOf<HorariosAsignaturaRequest>() }
    val isLoading = remember { mutableStateOf(false) }
    val listState = rememberLazyListState()
    var horariosAsignatura by remember { mutableStateOf<List<HorariosAsignaturaRequest>?>(null) }

    horariosAsignatura = firstLoad(asignaturaId, ::getHorariosAsignaturaRequest)
    LaunchedEffect(horariosAsignatura) {
        if(horariosAsignatura!=null){
            nombreCarrerasList.clear()
            horariosAsignatura?.let {
                loadMoreItems(nombreCarrerasList, it)
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
            LazyColumn(state = listState, modifier = Modifier
                .weight(1f)
                .padding(bottom = 20.dp)) {
                items(nombreCarrerasList.size) { index ->
                    HorariosAsignaturaItem(user = nombreCarrerasList[index].dtHorarioDias[index].diaSemana + " de " +
                            nombreCarrerasList[index].dtHorarioDias[index].horaInicio + " a " +
                            nombreCarrerasList[index].dtHorarioDias[index].horaFin + "hs",
                        idC = nombreCarrerasList[index].idHorarioAsignatura) {
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
            Text(text = stringResource(id = R.string.txt_error_horario), textAlign = TextAlign.Center)
        }
    }
}

@Composable
fun HorariosAsignaturaItem(user: String, idC: Int, onSelected: (Int) -> Unit) {
    CarreraCard(nombre = user, onHeaderClicked = { onSelected(idC) })
}