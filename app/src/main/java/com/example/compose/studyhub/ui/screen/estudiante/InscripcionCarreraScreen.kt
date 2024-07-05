package com.example.compose.studyhub.ui.screen.estudiante

import CarreraRequest
import InscripcionCarreraRequest
import alertDialogDoc2
import android.annotation.SuppressLint
import android.service.autofill.UserData
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import com.example.compose.studyhub.domain.formatDate

import com.example.compose.studyhub.R
import com.example.compose.studyhub.R.string.txt_inscripciones
import com.example.compose.studyhub.R.string.txt_selectCarrera
import com.example.compose.studyhub.data.UserRepository
import com.example.compose.studyhub.domain.formatDate
import com.example.compose.studyhub.http.requests.getCarrerasRequest
import com.example.compose.studyhub.http.requests.inscripcionCarreraRequest
import com.example.compose.studyhub.ui.component.CarreraCard
import com.example.compose.studyhub.ui.component.ConfirmDialogBox
import com.example.compose.studyhub.ui.component.RallyAlertDialog
import com.example.compose.studyhub.ui.component.searchBar.LocalAccountsDataProvider
import com.example.compose.studyhub.ui.component.searchBar.SearchBarScreen
import com.example.compose.studyhub.ui.component.searchBar.*
import kotlinx.coroutines.CoroutineScope


import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect

import kotlinx.coroutines.launch
import java.util.Locale

@Composable
fun InscripcionCarreraScreen(

  onInscripcionCarreraSubmitted: (idCarrera: Int) -> Unit,
  onInscripcionCarreraConfirmed: () -> Unit,
  onNavUp: () -> Unit,
  onError: String? = null,
  onSuccess: String? = null

): DrawerState {
    val remIdCarrera = remember { mutableStateOf<Int?>(null) }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    var respone by remember { mutableStateOf<String?>(null) }
    val showConfirmationDialog = remember { mutableStateOf(false) }
    val showErrorDialog = remember { mutableStateOf(false) }

    LaunchedEffect(onError){
        if(onError!=null){
            println("71On error: $onError")
            if(onError==""){
                showConfirmationDialog.value = true
            }
            else{
                showErrorDialog.value = true
            }
        }
    }


    Column(modifier = Modifier.padding(top = 50.dp, bottom = 1.dp)) {
        if (remIdCarrera.value == null) {
            Carreras(
                modifier = Modifier.fillMaxWidth(),
                snackbarHostState = snackbarHostState,
                scope = scope,
                onHeaderClicked = { idC: Int? ->
                    if (idC != null) {
                        remIdCarrera.value = idC
                        onInscripcionCarreraSubmitted(remIdCarrera.value!!)
                        println("Este remIdCarrera : ${remIdCarrera.value}")
                    }

                })
            AlertCard()
            Spacer(Modifier.height(RallyDefaultPadding))
            AccountsCard(
                onClickSeeAll = { },
                onAccountClick = {  }
            )
            Spacer(Modifier.height(RallyDefaultPadding))
            BillsCard(
                onClickSeeAll = {  }
            )
        } else {
            //CarrerasScreen(carreraId = remIdCarrera.value !!)
        }


     /*   if (showConfirmationDialog.value) {
            ConfirmDialogBox(
                onDismissRequest = {
                    onInscripcionCarreraConfirmed()
                    showConfirmationDialog.value = false
                },
                dialogTitle = "Inscripción confirmada"
            )
        }
        if (showErrorDialog.value) {
            ConfirmDialogBox(
                onDismissRequest = {
                    showErrorDialog.value = false
                },
                dialogTitle = onError ?: "Error desconocido"
            )
        }
    }
    return DrawerState(DrawerValue.Closed)
}*/



   /*     if(showErrorDialog.value){
            ConfirmDialogBox(onDismissRequest = {
                showConfirmationDialog.value = false }, dialogTitle = "ERROR")
        }
*/

        if(showConfirmationDialog.value){
            ConfirmDialogBox(onDismissRequest = { onInscripcionCarreraConfirmed()
            ; showConfirmationDialog.value = false }, dialogTitle = onSuccess?:"")
        }
        if(showErrorDialog.value){
            ConfirmDialogBox(onDismissRequest = { onInscripcionCarreraConfirmed()
            ; showErrorDialog.value = false }, dialogTitle = "El estudiante ya tiene una inscripción activa.")
        }
    }
    return DrawerState(DrawerValue.Closed)
}


//47647900
/*
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CarrerasScreen(carreraId: Int) {
    val scope = rememberCoroutineScope()
    var checked by remember { mutableStateOf(true) }
    val snackbarHostState = remember { SnackbarHostState() }
    var response by remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(carreraId) {
        coroutineScope.launch {
            UserRepository.loggedInUser()?.let { id ->
                UserRepository.getToken()?.let { token ->
                    if (checked) {
                        inscripcionCarreraRequest(
                            token,
                            InscripcionCarreraRequest(carreraId, id, true)
                        ) { success, re ->
                            if (success) {
                                scope.launch {
                                    snackbarHostState.showSnackbar("$re")
                                    response = "$re"
                                }
                            } else {
                                scope.launch {
                                    snackbarHostState.showSnackbar("$re")
                                    response = "$re"
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
*/

@Composable
fun firstLoad2(checked: Boolean): List<CarreraRequest>? {
    var carreras by remember { mutableStateOf<List<CarreraRequest>?>(null) }
    LaunchedEffect(checked) {
        UserRepository.loggedInUser()?.let { user ->
            UserRepository.getToken()?.let { token ->
                if (checked) {
                    getCarrerasRequest(token) { success ->
                        carreras = success
                    }
                }
            }
        }
    }
    return carreras
}
@Composable
fun AlertCard() {
    var showDialog by remember { mutableStateOf(false) }
    val alertMessage = "Heads up, you've used up 90% of your Shopping budget for this month."

    if (showDialog) {
        RallyAlertDialog(
            onDismiss = {
                showDialog = false
            },
            bodyText = alertMessage,
            buttonText = "Dismiss".uppercase(Locale.getDefault())
        )
    }
    Card {
        Column {
            AlertHeader {
                showDialog = true
            }
            RallyDivider(
                modifier = Modifier.padding(start = RallyDefaultPadding, end = RallyDefaultPadding)
            )
            AlertItem(alertMessage)
        }
    }
}

@Composable
private fun AlertHeader(onClickSeeAll: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(RallyDefaultPadding)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        androidx.compose.material.Text(
            text = "Alerts", style = androidx.compose.material.MaterialTheme.typography.subtitle2, modifier = Modifier.align(Alignment.CenterVertically)
        )
        androidx.compose.material.TextButton(
            onClick = onClickSeeAll, contentPadding = PaddingValues(0.dp), modifier = Modifier.align(Alignment.CenterVertically)
        ) {
            androidx.compose.material.Text(
                text = "SEE ALL",
                style = androidx.compose.material.MaterialTheme.typography.button,
            )
        }
    }
}

@Composable
private fun AlertItem(message: String) {
    Row(
        modifier = Modifier
            .padding(RallyDefaultPadding) // Regard the whole row as one semantics node. This way each row will receive focus as
            // a whole and the focus bounds will be around the whole row content. The semantics
            // properties of the descendants will be merged. If we'd use clearAndSetSemantics instead,
            // we'd have to define the semantics properties explicitly.
            .semantics(mergeDescendants = true) {},
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        androidx.compose.material.Text(
            style = androidx.compose.material.MaterialTheme.typography.body2, modifier = Modifier.weight(1f), text = message
        )
        androidx.compose.material.IconButton(onClick = {}, modifier = Modifier
            .align(Alignment.Top)
            .clearAndSetSemantics {}) {
            androidx.compose.material.Icon(Icons.AutoMirrored.Filled.Sort, contentDescription = null)
        }
    }
}

@Composable
private fun <T> OverviewScreenCard(
    title: String,
    amount: Float,
    onClickSeeAll: () -> Unit,
    values: (T) -> Float,
    colors: (T) -> Color,
    data: List<T>,
    row: @Composable (T) -> Unit
) {
    androidx.compose.material.Card {
        Column {
            Column(Modifier.padding(RallyDefaultPadding)) {
                androidx.compose.material.Text(text = title, style = androidx.compose.material.MaterialTheme.typography.subtitle2)
                val amountText = "$" + formatAmount(
                    amount
                )
                androidx.compose.material.Text(text = amountText, style = androidx.compose.material.MaterialTheme.typography.h2)
            }
            OverViewDivider(data, values, colors)
            Column(Modifier.padding(start = 16.dp, top = 4.dp, end = 8.dp)) {
                data.take(SHOWN_ITEMS).forEach { row(it) }
                SeeAllButton(
                    modifier = Modifier.clearAndSetSemantics {
                        contentDescription = "All $title"
                    },
                    onClick = onClickSeeAll,
                )
            }
        }
    }
}
@Composable
private fun <T> OverViewDivider(
    data: List<T>,
    values: (T) -> Float,
    colors: (T) -> Color
) {
    Row(Modifier.fillMaxWidth()) {
        data.forEach { item: T ->
            Spacer(
                modifier = Modifier
                    .weight(values(item))
                    .height(1.dp)
                    .background(colors(item))
            )
        }
    }
}

@Composable
private fun AccountsCard(onClickSeeAll: () -> Unit, onAccountClick: (String) -> Unit) {
  //  val amount = UserData.accounts.map { account -> account.balance }.sum()
   /* OverviewScreenCard(
        title = stringResource(R.string.txt_selectCarrera),
        amount = amount,
        onClickSeeAll = onClickSeeAll,
        data = UserData.accounts,
        colors = { it.color },
        values = { it.balance }
    ) { account ->
        AccountRow(
            modifier = Modifier.clickable { onAccountClick(account.name) },
            name = account.name,
            number = account.number,
            amount = account.balance,
            color = account.color
        )
    }*/
}
@Composable
private fun BillsCard(onClickSeeAll: () -> Unit) {

}

@Composable
private fun SeeAllButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    androidx.compose.material.TextButton(
        onClick = onClick, modifier = modifier
            .height(44.dp)
            .fillMaxWidth()
    ) {
        androidx.compose.material.Text(stringResource(R.string.txt_close))
    }
}

private val RallyDefaultPadding = 12.dp

private const val SHOWN_ITEMS = 3

@Composable
fun Carreras(modifier: Modifier, snackbarHostState: SnackbarHostState, scope: CoroutineScope, onHeaderClicked: (Int) -> Unit) {
    val nombreCarrerasList = remember { mutableStateListOf<CarreraRequest>() }
    val isLoading = remember { mutableStateOf(false) }
    val listState = rememberLazyListState()
    var carreras by remember { mutableStateOf<List<CarreraRequest>?>(null) }
    var checked by remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()

    carreras = firstLoad2(checked)
    LaunchedEffect(carreras) {
        nombreCarrerasList.clear()
        carreras?.let {
            loadMoreCarrera(nombreCarrerasList, it)
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
            emails = LocalAccountsDataProvider.allUserContactAccounts,
            modifier = Modifier,
            navigateToDetail = { _, _ -> }
        )
        Text(
            text = stringResource(id = txt_selectCarrera),
            style = MaterialTheme.typography.headlineSmall,
        )
        if (carreras != null) {
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = 20.dp)
            ) {
                items(nombreCarrerasList.size) { index ->
                    CarreraItem(
                        user = nombreCarrerasList[index].nombre,
                        snackbarHostState,
                        scope ,
                        idC = nombreCarrerasList[index].idCarrera) {
                        onHeaderClicked(it)
                    }
                }
                if (isLoading.value) {
                    item {
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                        ) {
                            //CircularProgressIndicator()
                        }
                    }
                }
            }
           /* LaunchedEffect(listState) {
                snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }.collect { index ->
                    if (index == nombreCarrerasList.size - 1 && !isLoading.value && nombreCarrerasList.size <= carreras!!.size) {
                        isLoading.value = true
                        coroutineScope.launch {
                            delay(3000)
                            loadMoreCarrera(nombreCarrerasList, carreras!!)
                            isLoading.value = false
                        }
                    }
                }
            }*/
        } else {
            Text(
                text = stringResource(id = R.string.txt_error_solicitudes),
                textAlign = TextAlign.Center
            )
        }
    }
}


fun loadMoreCarrera(carrerasList: MutableList<CarreraRequest>, carreras: List<CarreraRequest>) {
    val currentSize = carrerasList.size
    val listLength = if ((carreras.size - currentSize) < 30) {
        (carreras.size - currentSize)
    } else {
        30
    }
    for (i in 0 until listLength) {
        carrerasList.add(carreras[currentSize + i])
    }
}


@Composable
fun CarreraItem(user: String, snackbarHostState: SnackbarHostState, scope: CoroutineScope, idC: Int, onSelected: (Int) -> Unit) {
    CarreraCard(nombre = user, onHeaderClicked = { onSelected(idC) })
}


