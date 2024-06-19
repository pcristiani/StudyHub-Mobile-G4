package com.example.compose.studyhub.ui.component.gestion

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.example.compose.studyhub.data.UserRepository
import com.example.compose.studyhub.util.HTMLTemplate


@Composable
fun WebViewComponent(idCarrera: Int, modifier: Modifier): WebView? {
    val HTML = UserRepository.getNombre()?.let { UserRepository.getApellido()
        ?.let { it1 -> HTMLTemplate(it, it1) } }
    var webView:WebView? = null

    if(HTML!=null){
        AndroidView(
            modifier = modifier,
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
    return webView
}
