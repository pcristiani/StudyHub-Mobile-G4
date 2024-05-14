package com.example.compose.studyhub

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.compose.studyhub.ui.theme.ThemeStudyHub

///
// * MainActivity --> Clase principal de la aplicación.
// * AppCompatActivity --> Clase base para actividades.
class MainActivity : AppCompatActivity() {
   override fun onCreate(savedInstanceState: Bundle?) {
      enableEdgeToEdge()

      super.onCreate(savedInstanceState)

      println("---->MainActivity") // * Establecemos el contenido de la vista de la actividad */
      setContent {
         ThemeStudyHub {
            StudyHubNavHost()
         }
      }
   }
}