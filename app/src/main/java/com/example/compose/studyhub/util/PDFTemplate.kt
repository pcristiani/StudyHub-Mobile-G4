package com.example.compose.studyhub.util

import CalificacionAsignaturaRequest
import CalificacionExamenRequest
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
import kotlinx.html.h2
import kotlinx.html.h3
import kotlinx.html.head
import kotlinx.html.html
import kotlinx.html.li
import kotlinx.html.p
import kotlinx.html.stream.createHTML
import kotlinx.html.style
import kotlinx.html.title
import kotlinx.html.ul


fun HTMLTemplate(listaCalificacionesAsignatura: List<CalificacionAsignaturaRequest>?, listaCalificacionesExamen: List<CalificacionExamenRequest>?): String{
    val nombre: String? = UserRepository.getNombre()
    val apellido: String? = UserRepository.getApellido()
    val email: String? = UserRepository.getEmail()
    val ci: String? = UserRepository.getCI()


    if (listaCalificacionesAsignatura != null) {
        listaCalificacionesAsignatura.forEach { item->
            val calificaciones = item.calificaciones
            calificaciones.forEach{
                item2-> println(item2.calificacion)
            }

        }
    }
    return createHTML().html {
        head{
            title{+"Escolaridad"}
        }
        body{
            h2{+"Escolaridad de $nombre $apellido" }
            p{+ "Email: $email"}
            p{+ "Cédula de Identidad: $ci"}
            h2{+"============================================="}


            h3{+ "Asignaturas: "}
            ul{
                if (listaCalificacionesAsignatura != null) {
                    listaCalificacionesAsignatura.forEach {item ->
                        li{+(item.asignatura + ", nota/s: ")}
                        ul{
                            item.calificaciones.forEach{calificacion ->
                                li{+(calificacion.calificacion.toString() + ". Resultado: " + calificacion.resultado) }
                            }

                        }
                    }
                }else{
                    p{+"No hay asignaturas aprobadas."}
                }
            }

            h3{+ "Exámenes: "}
            ul{
                if (listaCalificacionesExamen != null) {
                    listaCalificacionesExamen.forEach {item ->
                        li{+(item.asignatura + ", nota: " + item.calificacion + ". Resultado: " + item.resultado) }

                    }
                }else{
                    p{+"No hay exámenes aprobados."}
                }
            }
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