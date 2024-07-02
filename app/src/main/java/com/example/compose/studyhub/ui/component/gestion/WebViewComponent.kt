package com.example.compose.studyhub.ui.component.gestion

import CalificacionAsignaturaRequest
import CalificacionExamenRequest
import CarreraRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.example.compose.studyhub.data.UserRepository
import com.example.compose.studyhub.http.requests.getCalificacionesAsignaturasRequest
import com.example.compose.studyhub.http.requests.getCalificacionesExamenesRequest
import com.example.compose.studyhub.util.HTMLTemplate


@Composable
fun WebViewComponent(carrera: CarreraRequest, modifier: Modifier): WebView? {
    var listaCalificacionesAsignatura: List<CalificacionAsignaturaRequest>? = null
    var listaCalificacionesExamen: List<CalificacionExamenRequest>? = null
    var HTML by remember { mutableStateOf("") }
    val webView = remember { mutableStateOf<WebView?>(null) }

    fun updateHTML(){
        HTML = HTMLTemplate(carrera.nombre, listaCalificacionesAsignatura, listaCalificacionesExamen)
    }

    LaunchedEffect(carrera) {
        UserRepository.loggedInUser()?.let { idUsuario ->
            UserRepository.getToken()?.let { token ->
                getCalificacionesAsignaturasRequest(idUsuario, carrera.idCarrera, token) { success ->
                    if (success != null) {
                        listaCalificacionesAsignatura = success
                        updateHTML()
                    }
                }
                getCalificacionesExamenesRequest(idUsuario, carrera.idCarrera, token) { success ->
                    if (success != null) {
                        listaCalificacionesExamen = success
                        updateHTML()
                    }
                }
            }
        }
    }

    if(HTML!=""){
        AndroidView(
            modifier = modifier,
            factory = { context ->
                WebView(context)
                    .apply {
                        webViewClient = WebViewClient()
                        loadDataWithBaseURL(null, HTML, "text/html", "UTF-8", null)
                    }
            },
        ) {
            webView.value = it
            it.webViewClient = WebViewClient()
            it.loadDataWithBaseURL(null, HTML, "text/html", "UTF-8", null)
            println("Webview: $webView")

        }
        webView.value?.getSettings()?.useWideViewPort = true;
        webView.value?.getSettings()?.loadWithOverviewMode = true;
        return webView.value
    }else{
        return null
    }

}
