package com.example.compose.studyhub

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.compose.studyhub.services.PushNotificationService
import com.example.compose.studyhub.ui.navigation.MenuLateral
import com.example.compose.studyhub.ui.navigation.NavRoutes
import com.example.compose.studyhub.ui.navigation.TopBar
import com.example.compose.studyhub.ui.route.EditarPerfilRoute
import com.example.compose.studyhub.ui.screen.estudiante.GestionScreen
import com.example.compose.studyhub.ui.screen.estudiante.InscripcionScreen
import com.example.compose.studyhub.ui.screen.estudiante.NovedadesScreen
import com.example.compose.studyhub.ui.screen.estudiante.PlanEstudiosScreen
import com.example.compose.studyhub.ui.screen.estudiante.SolicitudesScreen
import com.example.compose.studyhub.ui.theme.ThemeStudyHub
import com.google.firebase.FirebaseApp

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

        // val navController = rememberNavController()
        // SetupNavGraph(navController = navController)
      }
    }
  }
}
@Composable
fun SetupNavGraph(navController: NavHostController) { // val backStackEntry = compositionLocalOf<NavBackStackEntry?> { null }
  val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

  PushNotificationService.requestNewToken(context = LocalContext.current)

  NavHost(navController = navController, startDestination = "screenNovedades") {


    composable(NavRoutes.NovedadesScreen) {
      MenuLateral(navController, drawerState, contenido = {
        ScreenNovedades(drawerState, navController)
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
      MenuLateral(navController, drawerState, contenido = {
        ScreenEditarPerfil(navController, drawerState)
      })
    }


    composable(NavRoutes.InicioScreen) {
      val navController = rememberNavController()
      StudyHubNavHost(navController)
    }
  }
}
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ScreenNovedades(drawerState: DrawerState, navController: NavHostController) {
  TopBar(drawerState)
  NovedadesScreen(navController)
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
fun ScreenEditarPerfil(navController: NavHostController, drawerState: DrawerState) {
  TopBar(drawerState)
  EditarPerfilRoute(
    onProfileEditSubmitted = { navController.navigate(NavRoutes.NovedadesScreen) },
    onNavUp = navController::navigateUp,
  )
}
@Preview
@Composable
fun NovedadesScreenPreview() {
  ThemeStudyHub {
    //TopBar(NovedadesScreen())
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
}/*
   private fun createNotificationChannel() {
      // Create the NotificationChannel, but only on API 26+ because
      // the NotificationChannel class is not in the Support Library.
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
         val name = R.string.channel_name.toString()
         val descriptionText = R.string.channel_description.toString()
         val importance = NotificationManager.IMPORTANCE_DEFAULT

         val channel = NotificationChannel("1", name, importance).apply {
            description = descriptionText
         }
         // Register the channel with the system.
         val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
         notificationManager.createNotificationChannel(channel)
      }
   }


  
   private fun sendNotification() {
      val builder = NotificationCompat.Builder(this, "1")
         .setSmallIcon(R.drawable.a)
         .setContentTitle(getString(R.string.app_title))
         .setContentText("Test")
         .setPriority(NotificationCompat.PRIORITY_DEFAULT)

      with(NotificationManagerCompat.from(this)) {
         notify(1, builder.build())
      }
   }

   
 */

