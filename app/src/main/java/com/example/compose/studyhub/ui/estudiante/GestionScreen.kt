package com.example.compose.studyhub.ui.estudiante

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.compose.studyhub.R
import com.example.compose.studyhub.data.UserRepository
import com.example.compose.studyhub.ui.theme.md_theme_dark_text
import com.example.compose.studyhub.util.HTMLTemplate
import com.example.compose.studyhub.util.exportAsPdf
import com.rajat.pdfviewer.compose.PdfRendererViewCompose

@Composable
fun GestionScreen(): DrawerState {
   Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
      /*Image(painter = painterResource(id = R.drawable.a19_dj_128), modifier = Modifier.size(120.dp), contentDescription = "Logo")


      Text("Gestion", style = MaterialTheme.typography.titleMedium, color = md_theme_dark_text)
       */

      Column(modifier = Modifier.padding(top = 100.dp, bottom = 30.dp)) {
         Gestion(modifier = Modifier
            .weight(1f)
            .padding(top = 20.dp, start = 20.dp, end = 20.dp))



      }
   }
   return DrawerState(DrawerValue.Closed)
}


@Composable
fun Gestion(modifier: Modifier){
   val HTML = UserRepository.getNombre()?.let { UserRepository.getApellido()
      ?.let { it1 -> HTMLTemplate(it, it1) } }
   var webView:WebView? = null
   val context = LocalContext.current

   Column(
      modifier = modifier.fillMaxWidth(),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally,
   ) {



      /*
      PdfRendererViewCompose(
         modifier = modifier.fillMaxWidth().weight(1f),
         url = "https://ia800205.us.archive.org/12/items/gameoflifehowtop00shin/gameoflifehowtop00shin.pdf",
         lifecycleOwner = LocalLifecycleOwner.current
      )
       */

      println(HTML)

      if(HTML != null){
         AndroidView(
            modifier = modifier.fillMaxWidth().weight(1f),
            factory = { context ->
               WebView(context)
                  .apply {
                     webViewClient = WebViewClient()
                     loadDataWithBaseURL(null, HTML, "text/html", "UTF-8", null)
                  }
            },
         ){
            webView = it
            it.webViewClient = WebViewClient()
            it.loadDataWithBaseURL(null, HTML, "text/html", "UTF-8", null)
         }
      }



      Button(onClick = {exportAsPdf(webView, context)}, modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)){
         Text(text = stringResource(id = R.string.download_resume))
      }
   }
}


@Preview
@Composable
fun GestionScreenPreview() {
   GestionScreen()
}
