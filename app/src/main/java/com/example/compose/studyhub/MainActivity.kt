package com.example.compose.studyhub

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.compose.studyhub.ui.estudiante.GestionScreen
import com.example.compose.studyhub.ui.estudiante.InscripcionScreen
import com.example.compose.studyhub.ui.estudiante.NovedadesScreen
import com.example.compose.studyhub.ui.estudiante.PlanEstudiosScreen
import com.example.compose.studyhub.ui.estudiante.SolicitudesScreen
import com.example.compose.studyhub.ui.navigation.MenuLateral
import com.example.compose.studyhub.ui.navigation.TopBar
import com.example.compose.studyhub.ui.theme.ThemeStudyHub

///
// * MainActivity --> Clase principal de la aplicación.
// * AppCompatActivity --> Clase base para actividades.
class MainActivity: AppCompatActivity() {
   
   override fun onCreate(savedInstanceState: Bundle?) {
      enableEdgeToEdge()
      super.onCreate(savedInstanceState)
      
      setContent {
         ThemeStudyHub {
            StudyHubNavHost() //
            //  val navController = rememberNavController()
            // SetupNavGraph(navController = navController)
         }
      }
   }
}


@Composable
fun SetupNavGraph(navController: NavHostController) {
   val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
   
   NavHost(navController = navController, startDestination = "screenNovedades") {
      composable("screenNovedades") {
         MenuLateral(navController, drawerState, contenido = {
            ScreenNovedades(navController, drawerState)
         })
      }
      composable("screenEstudios") {
         MenuLateral(navController, drawerState, contenido = {
            ScreenEstudios(navController, drawerState)
         })
      }
      composable("screenInscripciones") {
         MenuLateral(navController, drawerState, contenido = {
            ScreenInscripciones(navController, drawerState)
         })
      }
      composable("screenSolicitudes") {
         MenuLateral(navController, drawerState, contenido = {
            ScreenSolicitudes(navController, drawerState)
         })
      }
      composable("screenGestion") {
         MenuLateral(navController, drawerState, contenido = {
            ScreenGestion(navController, drawerState)
         })
      }
   }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ScreenNovedades(navController: NavHostController, drawerState: DrawerState) {
   Scaffold(topBar = {
      TopBar(drawerState)
   }) {
      Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
         NovedadesScreen()
      }
   }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ScreenEstudios(navController: NavHostController, drawerState: DrawerState) {
   val scope = rememberCoroutineScope()
   Scaffold(topBar = {
      TopBar(drawerState)
   }) {
      Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
         Text("PLAN DE ESTUDIOS")
         PlanEstudiosScreen()
      }
   }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ScreenInscripciones(navController: NavHostController, drawerState: DrawerState) {
   Scaffold(topBar = {
      TopBar(drawerState)
   }) {
      Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
         Text("INSCRIPCIONES")
         InscripcionScreen()
      }
   }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ScreenSolicitudes(navController: NavHostController, drawerState: DrawerState) {
   Scaffold(topBar = {
      TopBar(drawerState)
   }) {
      Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
         Text("SOLICITUDES")
         SolicitudesScreen()
      }
   }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ScreenGestion(navController: NavHostController, drawerState: DrawerState) {
   Scaffold(topBar = {
      TopBar(drawerState)
   }) {
      Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
         Text("GESTIÓN")
         GestionScreen()
      }
   }
}


@Preview
@Composable
fun TopBarPreview() {
   ThemeStudyHub {
      TopBar(NovedadesScreen())
   }
}
