package com.example.compose.studyhub.ui.component.inscripciones

import ExamenRequest
import android.os.Build
import androidx.annotation.RequiresApi
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
import com.example.compose.studyhub.http.requests.getExamenesAsignatura
import com.example.compose.studyhub.ui.component.HorarioCard
import com.example.compose.studyhub.util.InfiniteScrolling.loadMoreItems
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ExamenesDeAsignatura(modifier: Modifier, asignaturaId: Int, onHeaderClicked: (Int) -> Unit) {
    val examenesList = remember { mutableStateListOf<ExamenRequest>() }
    val isLoading = remember { mutableStateOf(false) }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    var examenes by remember { mutableStateOf<List<ExamenRequest>?>(null) }

    examenes = firstLoad(asignaturaId)
    LaunchedEffect(examenes) {
        examenesList.clear()
        examenes?.let {
            loadMoreItems(examenesList, it)
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 70.dp, bottom = 1.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp), horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(id = txt_selectHorario),
            style = MaterialTheme.typography.headlineSmall,
        )

        if (examenes != null) {
            LazyColumn(
                state = listState, modifier = Modifier
                    .weight(1f)
                    .padding(bottom = 20.dp)
            ) {
                items(examenesList.size) { index ->
                    ExamenesDeAsignaturaItem(
                        user = "Periodo " + examenesList[index].periodoExamen + "   -   " + format(
                            examenesList[index].fechaHora
                        ), idC = examenesList[index].idExamen
                    ) {
                        onHeaderClicked(it)
                    }
                }
                if (isLoading.value) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {}
                    }
                }
            }
            LaunchedEffect(listState) {
                snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }.collect { index ->
                    if (index == examenesList.size - 1 && !isLoading.value && examenesList.size <= (examenes?.size ?: 0)) {
                        isLoading.value = true
                        coroutineScope.launch {
                            delay(3000)
                            loadMoreItems(examenesList, examenes!!)
                            isLoading.value = false
                        }
                    }
                }
            }
        } else {
            Text(
                text = stringResource(id = R.string.no_item_found), modifier = Modifier.padding(16.dp)
            )
            Text(text = stringResource(id = R.string.txt_error_horario), textAlign = TextAlign.Center)
        }
    }
}

@Composable
private fun firstLoad(asignaturaId: Int): List<ExamenRequest>? {
    var carreras by remember { mutableStateOf<List<ExamenRequest>?>(null) }
    UserRepository.loggedInUser()?.let { id ->
        UserRepository.getToken()?.let { token ->
            getExamenesAsignatura(asignaturaId, token) { responde ->
                carreras = responde
            }
        }
    }
    return carreras
}

@RequiresApi(Build.VERSION_CODES.O)
fun format(fechaStr: String): String {
    val formatterEntrada = DateTimeFormatter.ISO_LOCAL_DATE_TIME
    val fecha = LocalDateTime.parse(fechaStr, formatterEntrada)
    val formatterSalida = DateTimeFormatter.ofPattern("dd/MM/yyyy'  'HH:mm")
    val fechaFormateada = fecha.format(formatterSalida)
    return fechaFormateada + "hs"
}

@Composable
fun ExamenesDeAsignaturaItem(user: String, idC: Int, onSelected: (Int) -> Unit) {
    HorarioCard(nombre = user, onHeaderClicked = { onSelected(idC) })
}
