package com.example.compose.studyhub.ui.component.searchBar

import AsignaturaRequest
import CarreraRequest
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.foundation.layout.*
import com.example.compose.studyhub.util.InfiniteScrolling.firstLoad
import com.example.compose.studyhub.util.InfiniteScrolling.loadMoreItems
import androidx.compose.ui.res.stringResource
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.compose.studyhub.R
import com.example.compose.studyhub.data.UserRepository
import com.example.compose.studyhub.http.requests.getAsignaturasDeCarreraRequest
import com.example.compose.studyhub.http.requests.inscripcionesCarreraRequest
import com.example.compose.studyhub.ui.component.inscripciones.AsignaturaItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchList(
    items: List<CarreraRequest>, selectedItem: CarreraRequest?, modifier: Modifier = Modifier, onItemSelected: (CarreraRequest) -> Unit) {
    var query by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    val searchResults = remember { mutableStateListOf<CarreraRequest>() }
    val nombreCarreras = remember { mutableStateListOf<CarreraRequest>() }
    var carreras by remember { mutableStateOf<List<CarreraRequest>?>(null) }
    val listState = rememberLazyListState()
    carreras = firstLoad(UserRepository.loggedInUser() ?: 0, ::inscripcionesCarreraRequest)

    LaunchedEffect(query) {
        searchResults.clear()
        if (query.isNotEmpty()) {
            searchResults.addAll(items.filter {
                it.nombre.startsWith(query, ignoreCase = true)
            })
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
                    Icon(imageVector = (Icons.Default.Clear), contentDescription = stringResource(id = R.string.back_button), modifier = Modifier
                        .padding(start = 1.dp)
                        .padding(16.dp)
                        .clickable {
                            active = false
                            query = ""
                        })
                } else {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = stringResource(id = R.string.search),
                        modifier = Modifier.padding(start = 2.dp),
                    )
                }
            },
        ) {}

        LazyColumn(state = listState, modifier = Modifier) {
            items(if (query.isEmpty()) nombreCarreras else searchResults) { carrera ->
                AsignaturaItem(
                    user = carrera.nombre, idC = carrera.idCarrera
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
    items: List<AsignaturaRequest>, selectedItem: AsignaturaRequest?, carreraId: Int, modifier: Modifier = Modifier, onItemSelected: (AsignaturaRequest) -> Unit) {
    var query by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    val searchResults = remember { mutableStateListOf<AsignaturaRequest>() }
    val nombreAsignaturas = remember { mutableStateListOf<AsignaturaRequest>() }
    var asignaturas by remember { mutableStateOf<List<AsignaturaRequest>?>(null) }
    val listState = rememberLazyListState()

    asignaturas = firstLoad(carreraId, ::getAsignaturasDeCarreraRequest)

    LaunchedEffect(query) {
        searchResults.clear()
        if (query.isNotEmpty()) {
            searchResults.addAll(items.filter {
                it.nombre.startsWith(query, ignoreCase = true)
            })
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
                    Icon(imageVector = (Icons.Default.Clear), contentDescription = stringResource(id = R.string.back_button), modifier = Modifier
                        .padding(start = 1.dp)
                        .padding(16.dp)
                        .clickable {
                            active = false
                            query = ""
                        })
                } else {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = stringResource(id = R.string.search),
                        modifier = Modifier.padding(start = 2.dp),
                    )
                }
            },
        ) {}

        LazyColumn(state = listState, modifier = Modifier) {
            items(if (query.isEmpty()) nombreAsignaturas else searchResults) { asignaturas ->
                AsignaturaItem(
                    user = asignaturas.nombre, idC = asignaturas.idAsignatura
                ) {
                    onItemSelected(asignaturas)
                }
            }
        }
    }
}
