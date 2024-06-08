package com.example.compose.studyhub

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.compose.studyhub.services.PushNotificationService
import com.example.compose.studyhub.ui.estudiante.GestionScreen
import com.example.compose.studyhub.ui.estudiante.InscripcionScreen
import com.example.compose.studyhub.ui.estudiante.NovedadesScreen
import com.example.compose.studyhub.ui.estudiante.PlanEstudiosScreen
import com.example.compose.studyhub.ui.estudiante.SolicitudesScreen
import com.example.compose.studyhub.ui.navigation.MenuLateral
import com.example.compose.studyhub.ui.navigation.NavRoutes
import com.example.compose.studyhub.ui.navigation.TopBar
import com.example.compose.studyhub.ui.route.InicioRoute
import com.example.compose.studyhub.ui.route.LoginRoute
import com.example.compose.studyhub.ui.route.RegisterRoute
import com.example.compose.studyhub.ui.theme.ThemeStudyHub
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging


///
// * MainActivity --> Clase principal de la aplicación.
// * AppCompatActivity --> Clase base para actividades.

class MainActivity: AppCompatActivity() {

   override fun onCreate(savedInstanceState: Bundle?) {
      enableEdgeToEdge()
      super.onCreate(savedInstanceState)
      FirebaseApp.initializeApp(this)

      setContent {
         ThemeStudyHub {
            StudyHubNavHost() //
            //val navController = rememberNavController()
            //SetupNavGraph(navController = navController)
         }
      }
   }
}




@Composable
fun SetupNavGraph(navController: NavHostController) {
   //val backStackEntry = compositionLocalOf<NavBackStackEntry?> { null }
   val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)



   NavHost(navController = navController, startDestination = "screenNovedades") {

      composable(NavRoutes.NovedadesScreen) {
         MenuLateral(navController, drawerState, contenido = {
            ScreenNovedades(drawerState)
         })
      }
      composable(NavRoutes.EstudiosScreen) {
         MenuLateral(navController, drawerState, contenido = {
            ScreenEstudios(drawerState)
         })
      }
      composable(NavRoutes.InscripcionScreen) {
         MenuLateral(navController, drawerState, contenido = {
            ScreenInscripciones(drawerState)
         })
      }
      composable(NavRoutes.SolicitudesScreen) {
         MenuLateral(navController, drawerState, contenido = {
            ScreenSolicitudes(drawerState)
         })
      }
      composable(NavRoutes.GestionScreen) {
         MenuLateral(navController, drawerState, contenido = {
            ScreenGestion(drawerState)
         })
      }

      composable(NavRoutes.EditarPerfilScreen) {
         EditarPerfilScreen(drawerState) }
   }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ScreenNovedades(drawerState: DrawerState) {
   TopBar(drawerState)
   NovedadesScreen()
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ScreenEstudios(drawerState: DrawerState) {
   TopBar(drawerState)
   PlanEstudiosScreen()

}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ScreenInscripciones(drawerState: DrawerState) {
   TopBar(drawerState)
   InscripcionScreen()
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ScreenSolicitudes(drawerState: DrawerState) {
   TopBar(drawerState)
   SolicitudesScreen()
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ScreenGestion(drawerState: DrawerState) {
   TopBar(drawerState)
   GestionScreen()
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun EditarPerfilScreen(drawerState: DrawerState) {
   TopBar(drawerState)
   EditarPerfilScreen(drawerState)
}


@Preview
@Composable
fun NovedadesScreenPreview() {
   ThemeStudyHub {
      TopBar(NovedadesScreen())
   }
}


@Preview
@Composable
fun PlanEstudiosScreenPreview() {
   ThemeStudyHub {
      TopBar(PlanEstudiosScreen())
   }
}


@Preview
@Composable
fun InscripcionScreenPreview() {
   ThemeStudyHub {
      TopBar(InscripcionScreen())
   }
}


@Preview
@Composable
fun SolicitudesScreenPreview() {
   ThemeStudyHub {
      TopBar(SolicitudesScreen())
   }
}


@Preview
@Composable
fun GestionScreenPreview() {
   ThemeStudyHub {
      TopBar(GestionScreen())
   }
}

