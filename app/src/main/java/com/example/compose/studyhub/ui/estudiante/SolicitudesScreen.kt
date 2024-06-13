package com.example.compose.studyhub.ui.estudiante

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
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
import androidx.compose.runtime.rememberUpdatedState
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
    var asignaturas by remember { mutableStateOf<List<AsignaturaRequest>?>(null) }
    var checked by remember { mutableStateOf(true) }

    val currentChecked by rememberUpdatedState(checked)








    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(id = R.string.txt_solicitudes),
            style = MaterialTheme.typography.headlineSmall,

            )








        Row(modifier = Modifier.fillMaxWidth().padding(start = 10.dp)){
            Box(modifier = Modifier
                .size(50.dp, 60.dp)
                .padding(bottom=10.dp, start = 0.dp)
            ){
                IconButton(
                    onClick = {},
                    modifier = Modifier.align(Alignment.CenterStart)
                ){
                    Icon(imageVector = Icons.Filled.CalendarMonth, contentDescription = "Settings")
                }
            }
            Spacer(modifier = Modifier.padding(start = 10.dp))

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
            Spacer(modifier = Modifier.padding(start = 10.dp))

            Box(modifier = Modifier
                .size(50.dp, 60.dp)
                .padding(bottom=10.dp)
                ){
                IconButton(
                    onClick = {},
                    modifier = Modifier.align(Alignment.CenterEnd)
                ){
                    Icon(imageVector = Icons.Filled.Search, contentDescription = "Settings")
                }
            }

        }

/*
        Row(modifier = Modifier){
            SearchBar (
                query = "Buscar asignatura",
                onQueryChange = {},
                onSearch = {},
                active = false,
                onActiveChange = {},
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            ){

            }
        }

*/

        asignaturas = firstLoad(checked)

        println(asignaturas)



        if(asignaturas != null){

            LaunchedEffect(Unit) {
                loadMoreAsignaturas(asignaturasList, asignaturas!!)
            }


            LazyColumn(
                state = listState,
                modifier = Modifier.weight(1f).padding(bottom = 20.dp)
            ) {
                items(asignaturasList.size) { index ->
                    UserItem(user = asignaturasList[index])
                }
                if (isLoading.value) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ){
                        CircularProgressIndicator(
                            modifier = Modifier
                                .padding(16.dp)
                                .align(Alignment.Center)
                        )
                    }}
                }
            }
            LaunchedEffect(listState) {
                snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
                    .collect { index ->
                        if (index == asignaturasList.size && !isLoading.value && asignaturasList.size <= asignaturas!!.size) {
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


@Composable
fun firstLoad(checked: Boolean): List<AsignaturaRequest>?{
    var asignaturas by remember { mutableStateOf<List<AsignaturaRequest>?>(null) }

    if(checked == true){
        /*
        UserRepository.loggedInUser()?.let {
            UserRepository.getToken()?.let { it1 ->
                getAsignaturasAprobadasRequest(it, it1){ success ->
                    if (success != null) {
                        asignaturas = success
                        asignaturas!!.forEach { println("Asignatura: $it") }

                    }
                }
            }
        }
        */
        asignaturas = null
    }
    else{
        UserRepository.loggedInUser()?.let {
            UserRepository.getToken()?.let { it1 ->
                getAsignaturasNoAprobadasRequest(it, it1){ success ->
                    if (success != null) {
                        asignaturas = success
                        asignaturas!!.forEach { println("Asignatura: $it") }
                    }
                }
            }
        }
    }
    return asignaturas
}


suspend fun loadMoreAsignaturas(asignaturasList: MutableList<String>, asignaturas: List<AsignaturaRequest>) {
    val currentSize = asignaturasList.size
    var listLength: Int
    if ((asignaturas.size - asignaturasList.size) < 30){
        listLength = (asignaturas.size - asignaturasList.size)-1
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