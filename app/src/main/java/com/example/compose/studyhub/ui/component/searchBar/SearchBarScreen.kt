package com.example.compose.studyhub.ui.component.searchBar

import AsignaturaRequest
import CarreraRequest
import CarrerassRequest
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DockedSearchBar

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.foundation.layout.*
import com.example.compose.studyhub.util.InfiniteScrolling.firstLoad
import com.example.compose.studyhub.util.InfiniteScrolling.loadMoreItems
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.studyhub.R
import com.example.compose.studyhub.data.Account
import com.example.compose.studyhub.data.UserRepository
import com.example.compose.studyhub.data.UserRepository.user
import com.example.compose.studyhub.http.requests.getAsignaturasDeCarreraRequest
import com.example.compose.studyhub.http.requests.inscripcionesCarreraRequest
import com.example.compose.studyhub.ui.component.inscripciones.AsignaturaItem
import com.example.compose.studyhub.ui.screen.estudiante.CarreraItem
import com.example.compose.studyhub.ui.screen.estudiante.RowCarrera
import com.example.compose.studyhub.util.InfiniteScrolling.firstLoad
import com.example.compose.studyhub.util.InfiniteScrolling.loadMoreItems
import java.util.Locale.filter

enum class ReplyContentType {
    SINGLE_PANE, DUAL_PANE
}

/*@Composable
fun SearchBarScreen(emails: List<CarreraRequest>,  modifier: Modifier = Modifier, navigateToDetail: (Int, ReplyContentType) -> String): List<CarreraRequest> {
      Box(modifier = modifier .fillMaxWidth()){
       SearchList(
            emails = emails,
          //  carreras = carreras,
            onSearchItemSelected = { searchedEmail ->
                navigateToDetail(searchedEmail.idCarrera, ReplyContentType.SINGLE_PANE)
             },
             modifier = Modifier
                 .fillMaxWidth()
                 .padding(horizontal = 12.dp, vertical = 8.dp)
        )
    }
    return emails
    //return SearchList
   // return CarreraRequest(1, "aca","est","s", 1,true)
}*/

/*
@Composable
fun SearchBarScreen(
    carreras: List<String>, selectedCarrera: String?, modifier: Modifier = Modifier, onCarreraSelected: (String) -> Unit) {
   // var selectedCarrera by remember { mutableStateOf<String?>(null) }
    val scarreras = listOf("Ingeniería", "Medicina", "Arquitectura")
    val nombreCarrerasList = remember { mutableStateListOf<CarreraRequest>() }

    if (selectedCarrera != null) { // Muestra solo la carrera seleccionada
        Text(
            text = selectedCarrera, modifier = modifier.padding(16.dp)
        )
    } else {
        LazyColumn(modifier = modifier) {
            items(carreras) { carrera ->
                var carresras= listOf(scarreras, scarreras, scarreras)
               */
/* Text(text = carrera, modifier = Modifier
                    .clickable { onCarreraSelected(carrera) }
                    .padding(16.dp))*//*

                SearchList(
                    emails = carrera.let { nombreCarrerasList },
                        //  carreras = carreras,
                    onSearchItemSelected = { searchedEmail ->
                        onCarreraSelected(carrera)
                    }, modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchList(
    items: List<CarreraRequest>,
    selectedItem: String?,
    modifier: Modifier = Modifier,
    onItemSelected: (String) -> Unit
) {

    var query by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    val searchResults = remember { mutableStateListOf<CarreraRequest>() }
    val nombreCarreras = remember { mutableStateListOf<CarreraRequest>() }
    var carreras by remember { mutableStateOf<List<CarreraRequest>?>(null) }
    val coroutineScope = rememberCoroutineScope()
    val scarreras = listOf("Ingeniería", "Medicina", "Arquitectura")

    val nombreCarrerasList = remember { mutableStateListOf<CarreraRequest>() }
    val isLoading = remember { mutableStateOf(false) }
    val listState = rememberLazyListState()
    carreras = firstLoad(UserRepository.loggedInUser()?:0,::inscripcionesCarreraRequest)

    if (selectedItem != null) {
        // Muestra solo el ítem seleccionado
      Text(
            text = selectedItem,
            modifier = modifier.padding(16.dp)
        )
    //  if (carreras != null) {
      } else {
            Text(text = stringResource(id = R.string.txt_error_solicitudes), textAlign = TextAlign.Center)
        }
       LazyColumn(modifier = modifier) {
            items(items) { item ->
                Text(
                    text = item,
                    modifier = Modifier
                        .clickable { onItemSelected(item) }
                        .padding(16.dp)
                )
            }
        }
       // nombreCarrerasList.clear()
    CarreraItem(user = selectedItem!!, idC = nombreCarrerasList[0].idCarrera) {
          //onHeaderClicked(it)
   }
       LaunchedEffect(query) {
            searchResults.clear()
            if (query.isNotEmpty()) {
                searchResults.addAll(
                    items.filter {
                        it.nombre.startsWith(query, ignoreCase = true)
                    }
                )
            }
        }
    } else {
        // Muestra la lista de ítems para selección
        LaunchedEffect(carreras) {
            nombreCarrerasList.clear()
            carreras?.let {
                nombreCarrerasList.clear()
                loadMoreItems(nombreCarrerasList, it)
            }
        }
        LazyColumn(state = listState, modifier = Modifier
            // .weight(1f)
            .padding(bottom = 0.dp)) {
            items(nombreCarrerasList.size) { index ->
                CarreraItem(user = nombreCarrerasList[index].nombre, idC = nombreCarrerasList[index].idCarrera) {
                    //  onHeaderClicked(it)
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
        LaunchedEffect(query) {
            searchResults.clear()
            if (query.isNotEmpty()) {
                         searchResults.addAll(
                            items.filter {
                                it.nombre.startsWith(query, ignoreCase = true)
                            }
                         )
            }
        }
   */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchList(
    items: List<CarreraRequest>,
    selectedItem: CarreraRequest?,
    modifier: Modifier = Modifier,
    onItemSelected: (CarreraRequest) -> Unit
) {
    var query by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    val searchResults = remember { mutableStateListOf<CarreraRequest>() }
    val nombreCarreras = remember { mutableStateListOf<CarreraRequest>() }
    var carreras by remember { mutableStateOf<List<CarreraRequest>?>(null) }
    val listState = rememberLazyListState()
    carreras = firstLoad(UserRepository.loggedInUser() ?: 0, ::inscripcionesCarreraRequest)
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(query) {
        searchResults.clear()
        if (query.isNotEmpty()) {
            searchResults.addAll(
                items.filter {
                    it.nombre.startsWith(query, ignoreCase = true)
                }
            )
        }
    }

    LaunchedEffect(carreras) {
        nombreCarreras.clear()
        carreras?.let {
            loadMoreItems(nombreCarreras, it)
        }
    }

    Column(modifier = Modifier) {
        DockedSearchBar(
            modifier = modifier
                .height(75.dp)
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            query = query,
            onQueryChange = { query = it },
            onSearch = { active = false },
            active = active,
            onActiveChange = { active = it },

            placeholder = { Text(text = stringResource(id = R.string.search_bus)) },
            leadingIcon = {
                if (active) {
                    Icon(
                        imageVector = (Icons.Default.Clear),
                        contentDescription = stringResource(id = R.string.back_button),
                        modifier = Modifier.padding(start = 1.dp)
                            .padding(16.dp)
                            .clickable {
                                active = false
                                query = ""
                            }
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = stringResource(id = R.string.search),
                        modifier = Modifier.padding(start = 2.dp),
                    )
                }
            },
            ) {
        }

        LazyColumn(state = listState, modifier = Modifier) {
            items(if (query.isEmpty()) nombreCarreras else searchResults) { carrera ->
              AsignaturaItem(                 user = carrera.nombre,
                    idC = carrera.idCarrera
                ) {
                    onItemSelected(carrera)
                }
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchListAsignaturas(
    items: List<AsignaturaRequest>,
    selectedItem: AsignaturaRequest?,
    carreraId:Int,
    modifier: Modifier = Modifier,
    onItemSelected: (AsignaturaRequest) -> Unit
) {
    var query by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    val searchResults = remember { mutableStateListOf<AsignaturaRequest>() }
    val nombreAsignaturas = remember { mutableStateListOf<AsignaturaRequest>() }
    var asignaturas by remember { mutableStateOf<List<AsignaturaRequest>?>(null) }
  //  asignaturas = firstLoad(UserRepository.loggedInUser() ?: 0, ::getAsignaturasDeCarreraRequest)

    asignaturas = firstLoad(carreraId, ::getAsignaturasDeCarreraRequest)

    val listState = rememberLazyListState()

    LaunchedEffect(query) {
        searchResults.clear()
        if (query.isNotEmpty()) {
            searchResults.addAll(
                items.filter {
                    it.nombre.startsWith(query, ignoreCase = true)
                }
            )
        }
    }

    LaunchedEffect(asignaturas) {
        nombreAsignaturas.clear()
        asignaturas?.let {
            loadMoreItems(nombreAsignaturas, it)
        }
    }

    Column(modifier = Modifier) {
        DockedSearchBar(
            modifier = modifier
                .height(75.dp)
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            query = query,
            onQueryChange = { query = it },
            onSearch = { active = false },
            active = active,
            onActiveChange = { active = it },

            placeholder = { Text(text = stringResource(id = R.string.search_bus)) },
            leadingIcon = {
                if (active) {
                    Icon(
                        imageVector = (Icons.Default.Clear),
                        contentDescription = stringResource(id = R.string.back_button),
                        modifier = Modifier.padding(start = 1.dp)
                            .padding(16.dp)
                            .clickable {
                                active = false
                                query = ""
                            }
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = stringResource(id = R.string.search),
                        modifier = Modifier.padding(start = 2.dp),
                    )
                }
            },
        ) {
        }

        LazyColumn(state = listState, modifier = Modifier) {
            items(if (query.isEmpty()) nombreAsignaturas else searchResults) { asignaturas ->
                AsignaturaItem(user = asignaturas.nombre,
                    idC = asignaturas.idAsignatura
                ) {
                    onItemSelected(asignaturas)
                }
            }
        }
    }
}
