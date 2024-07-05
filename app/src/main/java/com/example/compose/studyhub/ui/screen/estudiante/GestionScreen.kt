package com.example.compose.studyhub.ui.screen.estudiante

import CarreraRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.compose.studyhub.R
import com.example.compose.studyhub.data.User
import com.example.compose.studyhub.data.UserRepository
import com.example.compose.studyhub.http.requests.inscripcionesCarreraRequest
import com.example.compose.studyhub.ui.component.gestion.ExpandableList
import com.example.compose.studyhub.ui.component.gestion.WebViewComponent
import com.example.compose.studyhub.ui.theme.md_theme_dark_text
import com.example.compose.studyhub.util.HTMLTemplate
import com.example.compose.studyhub.util.exportAsPdf
import com.rajat.pdfviewer.compose.PdfRendererViewCompose

@Composable
fun GestionScreen(): DrawerState {
   Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
      Column(modifier = Modifier.padding(top = 50.dp, bottom = 1.dp)) {
         Gestion(modifier = Modifier
            .weight(1f)
            .padding(top = 20.dp, start = 20.dp, end = 20.dp))
      }
   }
   return DrawerState(DrawerValue.Closed)
}

@Composable
fun Gestion(modifier: Modifier){
   val context = LocalContext.current
   val webView = remember { mutableStateOf<WebView?>(null) }

   Column(
      modifier = modifier.fillMaxWidth(),
      horizontalAlignment = Alignment.CenterHorizontally,
   ) {
      var listaCarreras: List<CarreraRequest>? = null
      val nombresCarrera = remember { mutableStateListOf<String>() }
      val idsCarrera = remember { mutableStateListOf<Int>() }
      val carreraSelected = remember { mutableStateOf<CarreraRequest?>(null) }

      //Retornar todas las carreras a las que el usuario está inscripto en las listas listaCarreras, nombresCarrera e idsCarrera
      UserRepository.loggedInUser()?.let {idUsuario -> UserRepository.getToken()
         ?.let {token -> inscripcionesCarreraRequest(idUsuario, token){success->
            if(success!=null){
               listaCarreras = success
               println(success)
               nombresCarrera.clear()
               idsCarrera.clear()
               listaCarreras?.forEach {
                  nombresCarrera.add(it.nombre)
                  idsCarrera.add(it.idCarrera)
                  println(listaCarreras)
               }
            }
         } } }

      //Lista expandible con todas las carreras a las que el usuario está inscripto
      ExpandableList(modifier=Modifier.padding(top = 25.dp, bottom = 10.dp,start = 20.dp, end = 20.dp)
          .animateContentSize(),
         headerTitle = carreraSelected.value?.nombre ?: stringResource(id = R.string.txt_selectCarrera), options = nombresCarrera, optionIds = idsCarrera, onOptionSelected={selectedId -> carreraSelected.value =
         listaCarreras?.find {it.idCarrera ==selectedId }
      })

       webView.value = carreraSelected.value?.let {
          WebViewComponent(
             it,
             modifier
                .fillMaxWidth()
                .weight(1f),
             )
       }

      if(webView.value==null && carreraSelected.value!=null){
         Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(top = 250.dp)){
            Text(text = stringResource(id = R.string.empty_carrera))
         }
         
      }else if(webView.value!=null){
            Button(onClick = {exportAsPdf(webView.value, context); println(webView.value)}, modifier = Modifier
               .fillMaxWidth()
               .padding(vertical = 16.dp)){
               Text(text = stringResource(id = R.string.download_resume))
            }
         }
   }
}


@Preview
@Composable
fun GestionScreenPreview() {
   GestionScreen()
}
