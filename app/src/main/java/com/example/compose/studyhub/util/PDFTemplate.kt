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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.viewinterop.AndroidView
import com.example.compose.studyhub.R
import com.example.compose.studyhub.data.UserRepository
import kotlinx.html.body
import kotlinx.html.div
import kotlinx.html.h1
import kotlinx.html.h2
import kotlinx.html.h3
import kotlinx.html.head
import kotlinx.html.html
import kotlinx.html.img
import kotlinx.html.li
import kotlinx.html.p
import kotlinx.html.span
import kotlinx.html.stream.createHTML
import kotlinx.html.style
import kotlinx.html.title
import kotlinx.html.ul
import kotlinx.html.unsafe
import okhttp3.internal.http2.Header


fun HTMLTemplate(nombreCarrera:String, listaCalificacionesAsignatura: List<CalificacionAsignaturaRequest>?, listaCalificacionesExamen: List<CalificacionExamenRequest>?): String{
    val nombre: String? = UserRepository.getNombre()
    val apellido: String? = UserRepository.getApellido()
    val email: String? = UserRepository.getEmail()
    val ci: String? = UserRepository.getCI()
    val dateTime: String = getCurrentDateTime()

    var cantCursadas: Int? = 0
    var cantExamenes: Int? = 0

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
            title{+"Certificado de Escolaridad"}
            style{
                unsafe{
                    raw("""body {
                            font-family: 'Helvetica', sans-serif;
                            color: #222;
                            margin: 20px;
                            }
                                .header, .section, .footer {
                            margin-bottom: 20px;
                        }
                            .header {
                                text-align: center;
                            }
                            .header h1 {
                            font-size: 22px;
                            font-weight: bold;
                        }
                            .header p {
                            font-size: 14px;
                        }
                            .section h2 {
                            font-size: 18px;
                            font-weight: bold;
                        }
                            .section p {
                            font-size: 12px;
                            margin: 5px 0;
                        }
                            .divider {
                                border-bottom: 1px solid #000;
                                margin: 10px 0;
                            }
                            .course-list, .exam-list {
                            margin-top: 10px;
                        }
                            .course-item, .exam-item {
                            display: flex;
                            justify-content: space-between;
                            font-size: 12px;
                        }""".trimIndent())
                }
            }
        }
        body{
            div("header"){
                img{src="https://frontstudyhub.vercel.app/static/media/logo-text.1b43604a02cff559bc6a.png"; alt="Logo"; width="160"}
                h1{+"Certificado de Escolaridad" }
                p{+ "Resultados finales"}
                p{+ "Emisión: $dateTime"}
            }

            div("section"){
                h2{ +nombreCarrera }
                div("divider"){}
                p{+"Nombre: $nombre $apellido"}
                p{+"Cédula: $ci"}
                p{+"Email: $email"}
            }

            div("section course-list"){
                h2{+ "Cursos"}
                if (listaCalificacionesAsignatura != null) {
                    listaCalificacionesAsignatura.forEach {item ->
                        div("course-item"){

                            span{+(item.asignatura)}
                            item.calificaciones.forEach{calificacion ->
                                if(calificacion.calificacion!=0){
                                    span{+("Calificación: ${calificacion.calificacion.toString()}")}
                                    span{+(calificacion.resultado) }
                                }

                            }
                        }
                        cantCursadas = cantCursadas!! + 1
                    }
                }else{
                    div("course-item"){
                        span{+"No hay materias aprobadas."}

                    }
                }
            }

            div("divider"){}

            div("section exam-list"){
                h2{+ "Exámenes"}
                if (listaCalificacionesExamen != null) {
                    listaCalificacionesExamen.forEach {item ->

                        if(item.calificacion!=0){
                            div("exam-item"){
                                span{+(item.asignatura)}
                                span{+("Calificación: ${item.calificacion}")}
                                span{+(item.resultado)}

                            }
                            cantExamenes = cantExamenes!! + 1
                        }
                    }
                }else{
                    div("exam-item"){
                        span{+"No hay exámenes aprobados."}

                    }
                }
            }

            div("divider"){}

            div("footer"){
                p{+"Cantidad cursadas: $cantCursadas"}
                p{+"Cantidad exámenes: $cantExamenes"}
                p{+"Total: ${cantCursadas!! + cantExamenes!!}"}
                p{+"Promedio general: "}
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