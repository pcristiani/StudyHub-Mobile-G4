package com.example.compose.studyhub.ui.component.gestion

import CalificacionAsignaturaRequest
import CalificacionExamenRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.example.compose.studyhub.data.UserRepository
import com.example.compose.studyhub.http.requests.getCalificacionesAsignaturasRequest
import com.example.compose.studyhub.http.requests.getCalificacionesExamenesRequest
import com.example.compose.studyhub.util.HTMLTemplate


@Composable
fun WebViewComponent(idCarrera: Int, modifier: Modifier): WebView? {



    var listaCalificacionesAsignatura: List<CalificacionAsignaturaRequest>? = null
    var listaCalificacionesExamen: List<CalificacionExamenRequest>? = null

    UserRepository.loggedInUser()
        ?.let { idUsuario ->
            UserRepository.getToken()
                ?.let { token ->

                    getCalificacionesAsignaturasRequest(idUsuario, idCarrera, token) { success ->
                        if (success != null) {
                            listaCalificacionesAsignatura = success

                            val listaCalificacionesA = listOf({})
                        }}

                    getCalificacionesExamenesRequest(idUsuario, idCarrera, token) { success ->

                        if (success != null) {
                            listaCalificacionesExamen = success
                        }
                    }
                }
        }





    val HTML = listaCalificacionesAsignatura?.let { listaCalificacionesExamen?.let { it1 ->
        HTMLTemplate(it,
            it1
        )
    } }




            var webView: WebView? = null

            if (HTML != null) {
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
                    webView = it
                    it.webViewClient = WebViewClient()
                    it.loadDataWithBaseURL(null, HTML, "text/html", "UTF-8", null)
                }
            }else{

            }

            return webView
        }
