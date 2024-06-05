package com.example.compose.studyhub.ui.estudiante

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.studyhub.R
import com.example.compose.studyhub.auth.SolicitudRequest
import com.example.compose.studyhub.http.solicitudesRequest
import com.example.compose.studyhub.ui.component.registrarEstudiante.QuestionWrapper
import com.example.compose.studyhub.ui.component.registrarEstudiante.simpleDateFormatPattern
import com.example.compose.studyhub.ui.theme.ThemeStudyHub
import com.example.compose.studyhub.ui.theme.md_theme_dark_text
import com.example.compose.studyhub.ui.theme.slightlyDeemphasizedAlpha
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone


@Composable
fun SolicitudesScreen(): DrawerState {
   Column(modifier = Modifier.padding(top = 100.dp, bottom = 6.dp)) {
      Solicitudes(modifier = Modifier
         .weight(1f)
         .padding(top = 20.dp, start = 20.dp))
   }
   return DrawerState(DrawerValue.Closed)
}


@Composable
fun Solicitudes(modifier: Modifier) {
   val users = remember { mutableStateListOf<String>() }
   val isLoading = remember { mutableStateOf(false) }
   val listState = rememberLazyListState()
   val coroutineScope = rememberCoroutineScope()
   var solicitudesNoAprobadas: List<SolicitudRequest>? = null


   solicitudesRequest(){success ->
      if (success != null) {
         solicitudesNoAprobadas = success
      }
   }

   LaunchedEffect(Unit) {
      loadMoreUsers(users)
   }

   Column(
      modifier = modifier.fillMaxWidth(),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally
   ) {
      Text(
         text = stringResource(id = R.string.txt_solicitudes),
         style = MaterialTheme.typography.headlineSmall
      )

   if(solicitudesNoAprobadas != null){

         LazyColumn(
            state = listState,
            modifier = Modifier.weight(1f)
         ) {
            items(users.size) { index ->
               UserItem(user = users[index])
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
                  if (index == users.size - 1 && !isLoading.value) {
                     isLoading.value = true
                     coroutineScope.launch {
                        delay(3000) // Simulate network delay
                        loadMoreUsers(users)
                        isLoading.value = false
                     }
                  }
               }
         }
   }else{
      Text(text = "No se encontraron solicitudes no aprobadas")
   }
   }


}



suspend fun loadMoreUsers(users: MutableList<String>) {
   val currentSize = users.size
   for (i in 1..30) {
      users.add("Usuario con ID: ${currentSize + i}")
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
   Text(
      text = user,
      modifier = Modifier.padding(8.dp)
   )
}