package com.example.compose.studyhub.util

import android.content.Context
import android.print.PrintAttributes
import android.print.PrintManager
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.example.compose.studyhub.data.UserRepository
import kotlinx.html.body
import kotlinx.html.h1
import kotlinx.html.head
import kotlinx.html.html
import kotlinx.html.stream.createHTML
import kotlinx.html.title



fun HTMLTemplate(nombre: String, apellido: String): String{
    return createHTML().html {
        head{
            title{+"Escolaridad"}
        }
        body{
            h1{+"Escolaridad de $nombre $apellido" }
        }
    }
}

fun exportAsPdf(webView: WebView?, context: Context) {
    if (webView != null) {
        val printManager = context.getSystemService(Context.PRINT_SERVICE) as PrintManager
        val printAdapter =
            webView.createPrintDocumentAdapter("TestPDF")
        val printAttributes = PrintAttributes.Builder()
            .setMediaSize(PrintAttributes.MediaSize.ISO_A4)
            .build()
        printManager.print("TestPDF",
            printAdapter,
            printAttributes)
    }
}