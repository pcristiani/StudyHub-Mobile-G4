package com.example.compose.studyhub.ui.estudiante

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.studyhub.R
import com.example.compose.studyhub.auth.AsignaturaRequest
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
    var asignaturas: List<AsignaturaRequest>? = null






    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(id = R.string.txt_solicitudes),
            style = MaterialTheme.typography.headlineSmall,

            )



        var checked by remember { mutableStateOf(true) }


        Row(modifier = Modifier){
            Text(text = "Pendientes", modifier = Modifier.padding(top=15.dp), style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.padding(start = 8.dp))
            Switch(
                colors = SwitchDefaults.colors(
                    //checkedTrackColor = colorResource(id = R.color.teal_200),
                ),
                checked = checked,
                onCheckedChange = {
                    checked = it
                }
            )
            Text(text = "Aprobadas", modifier = Modifier.padding(top=15.dp, start=6.dp), style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.padding(start = 8.dp))
        }




        if(checked == true){
            UserRepository.loggedInUser()?.let {
                UserRepository.getToken()?.let { it1 ->
                    getAsignaturasAprobadasRequest(it, it1){ success ->
                        if (success != null) {
                            asignaturas = success
                        }
                    }
                }
            }
        }
        else{
            UserRepository.loggedInUser()?.let {
                UserRepository.getToken()?.let { it1 ->
                    getAsignaturasNoAprobadasRequest(it, it1){ success ->
                        if (success != null) {
                            asignaturas = success
                        }
                    }
                }
            }
        }



        val listPrev = listOf(1, 2)

        val asig1 = AsignaturaRequest(1, 1, "Matemática", 12, "Materia test", "Fisico-química", true, true, listPrev)
        val asig2 = AsignaturaRequest(2, 1, "Física", 12, "Materia test", "Fisico-química", true, true, listPrev)
        val asig3 = AsignaturaRequest(3, 1, "Química", 12, "Materia test", "Fisico-química", true, true, listPrev)
        val asig4 = AsignaturaRequest(4, 1, "Biología", 12, "Materia test", "Fisico-química", true, true, listPrev)
        val asig5 = AsignaturaRequest(5, 1, "Geografía", 12, "Materia test", "Fisico-química", true, true, listPrev)
        val asig6 = AsignaturaRequest(6, 1, "Álgebra", 12, "Materia test", "Fisico-química", true, true, listPrev)
        val asig7 = AsignaturaRequest(6, 1, "Álgebra", 12, "Materia test", "Fisico-química", true, true, listPrev)
        val asig8 = AsignaturaRequest(6, 1, "Álgebra", 12, "Materia test", "Fisico-química", true, true, listPrev)
        val asig9 = AsignaturaRequest(6, 1, "Álgebra", 12, "Materia test", "Fisico-química", true, true, listPrev)
        val asig10 = AsignaturaRequest(6, 1, "Álgebra", 12, "Materia test", "Fisico-química", true, true, listPrev)
        val asig11 = AsignaturaRequest(6, 1, "Álgebra", 12, "Materia test", "Fisico-química", true, true, listPrev)
        val asig12 = AsignaturaRequest(6, 1, "Álgebra", 12, "Materia test", "Fisico-química", true, true, listPrev)
        val asig13 = AsignaturaRequest(6, 1, "Álgebra", 12, "Materia test", "Fisico-química", true, true, listPrev)
        val asig14 = AsignaturaRequest(6, 1, "Álgebra", 12, "Materia test", "Fisico-química", true, true, listPrev)
        val asig15 = AsignaturaRequest(6, 1, "Álgebra", 12, "Materia test", "Fisico-química", true, true, listPrev)
        val asig16 = AsignaturaRequest(6, 1, "Álgebra", 12, "Materia test", "Fisico-química", true, true, listPrev)
        val asig17 = AsignaturaRequest(6, 1, "Álgebra", 12, "Materia test", "Fisico-química", true, true, listPrev)
        val asig18 = AsignaturaRequest(6, 1, "Álgebra", 12, "Materia test", "Fisico-química", true, true, listPrev)
        val asig19 = AsignaturaRequest(6, 1, "Álgebra", 12, "Materia test", "Fisico-química", true, true, listPrev)
        val asig20 = AsignaturaRequest(6, 1, "Álgebra", 12, "Materia test", "Fisico-química", true, true, listPrev)
        val asig21 = AsignaturaRequest(6, 1, "Álgebra", 12, "Materia test", "Fisico-química", true, true, listPrev)
        val asig22 = AsignaturaRequest(6, 1, "Álgebra", 12, "Materia test", "Fisico-química", true, true, listPrev)
        val asig23 = AsignaturaRequest(6, 1, "Álgebra", 12, "Materia test", "Fisico-química", true, true, listPrev)
        val asig24 = AsignaturaRequest(6, 1, "Álgebra", 12, "Materia test", "Fisico-química", true, true, listPrev)
        val asig25 = AsignaturaRequest(6, 1, "Álgebra", 12, "Materia test", "Fisico-química", true, true, listPrev)
        val asig26 = AsignaturaRequest(6, 1, "Álgebra", 12, "Materia test", "Fisico-química", true, true, listPrev)
        val asig27 = AsignaturaRequest(6, 1, "Álgebra", 12, "Materia test", "Fisico-química", true, true, listPrev)
        val asig28 = AsignaturaRequest(6, 1, "Álgebra", 12, "Materia test", "Fisico-química", true, true, listPrev)
        val asig29 = AsignaturaRequest(6, 1, "Álgebra", 12, "Materia test", "Fisico-química", true, true, listPrev)
        val asig30 = AsignaturaRequest(6, 1, "Álgebra", 12, "Materia test", "Fisico-química", true, true, listPrev)
        val asig31 = AsignaturaRequest(6, 1, "Álgebra", 12, "Materia test", "Fisico-química", true, true, listPrev)
        val asig32 = AsignaturaRequest(6, 1, "Álgebra", 12, "Materia test", "Fisico-química", true, true, listPrev)
        val asig33 = AsignaturaRequest(6, 1, "Álgebra", 12, "Materia test", "Fisico-química", true, true, listPrev)
        val asig34 = AsignaturaRequest(6, 1, "AUOGBaogs", 12, "Materia test", "Fisico-química", true, true, listPrev)
        
        
        
        


        asignaturas = listOf(asig1, asig2, asig3, asig4, asig5, asig6, asig7, asig8, asig9, asig10, asig11, asig12, asig13, asig14, asig15, asig16, asig17, asig18, asig19, asig20, asig21, asig22, asig23, asig24, asig25, asig26, asig27, asig28, asig29, asig30, asig31, asig32, asig33, asig34)


        if(asignaturas != null){

            LaunchedEffect(Unit) {
                loadMoreAsignaturas(asignaturasList, asignaturas!!)
            }


            LazyColumn(
                state = listState,
                modifier = Modifier.weight(1f)
            ) {
                items(asignaturasList.size) { index ->
                    UserItem(user = asignaturasList[index])
                }
                if (isLoading.value) {
                    item {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .padding(16.dp)
                                .align(Alignment.CenterHorizontally)
                        )
                    }
                }
            }
            LaunchedEffect(listState) {
                snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
                    .collect { index ->
                        if (index == asignaturasList.size - 1 && !isLoading.value && asignaturasList.size <= asignaturas!!.size) {
                            isLoading.value = true
                            coroutineScope.launch {
                                delay(3000) // Simulate network delay
                                loadMoreAsignaturas(asignaturasList, asignaturas!!)
                                isLoading.value = false
                            }
                        }
                    }
            }
        }else{

            Text(text = stringResource(id = R.string.txt_error_solicitudes),
                textAlign = TextAlign.Center)
        }
    }


}



suspend fun loadMoreAsignaturas(asignaturasList: MutableList<String>, asignaturas: List<AsignaturaRequest>) {
    val currentSize = asignaturasList.size
    var listLength: Int
    if (asignaturas.size < 30){
        listLength = asignaturas.size-1
    }else{
        listLength = 30
    }
    for (i in 1..listLength) {
        asignaturasList.add(asignaturas.get(currentSize+i).nombre)
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
        //modifier = Modifier.padding(8.dp)
    )
}