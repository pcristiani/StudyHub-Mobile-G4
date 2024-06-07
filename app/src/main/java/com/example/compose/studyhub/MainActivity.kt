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

/*
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

 */

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




   /*
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

}




/*

class MainActivity: AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
     super.onCreate(savedInstanceState)/*   setContentView(R.layout.activity_main) */


     setContent{
        ThemeStudyHub {
           InicioScreen(
              onLoginRegister = {navController.navigate("loginRegister") },
              onLoginInvitado = { /* Handle login as guest */ }
           )
        }
     }





    val loginRequest = LoginRequest("52275944", "GCBBp79Kz3Mr")

     RetrofitClient.api.login(loginRequest).enqueue(object: Callback<String> {
        override fun onResponse(call: Call<String>, response: Response<String>) {
           if (response.isSuccessful) {
              val token = response.body() // Procesar la respuesta del login


              if(token!=null){
                 val decodedResponse = decodeJWT(token)
              }

              //println("$decodedResponse")
              Toast.makeText(this@MainActivity, "Token: $token", Toast.LENGTH_SHORT).show()
           } else {
              Toast.makeText(this@MainActivity, "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
           }
        }

        override fun onFailure(call: Call<String>, t: Throwable) {
           Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
           println("Error: ${t.message}")
        }
     })
   }
}*/


@Composable
fun SetupNavGraph(navController: NavHostController) {
   val backStackEntry = compositionLocalOf<NavBackStackEntry?> { null }
   val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)



   NavHost(navController = navController, startDestination = "inicioScreen") {

      composable("inicioScreen") {
         /*InicioScreen(
            onLoginRegister = { navController.navigate("loginRegister") },
            onLoginInvitado = { /* Handle login as guest */ }
         )*/
         InicioRoute(onNavigateToLogin = { ci ->
            navController.navigate("loginScreen/$ci")
         },
            onLoginInvitado = {
               navController.navigate("homeScreen")
            })
      }


      composable(NavRoutes.LoginScreen) {
         val ci = it.arguments?.getString("ci")

         val context = LocalContext.current

         LoginRoute(
            ci = ci,
            onLoginSubmitted = {
               navController.navigate("screenNovedades")
               //PushNotificationService.requestNewToken(context)
            },
            onNavigateToRegister = {
               navController.navigate("registerScreen/$ci")
            },
            onLoginInvitado = {
               // Handle login as guest
            },
            onNavUp = {
               navController.popBackStack()
            }
         )
      }

      composable(NavRoutes.RegisterScreen) {
         val ci = it.arguments?.getString("ci")

         RegisterRoute(
            ci = ci,
            onRegisterSubmitted = {
               navController.popBackStack()
            },
            onLoginInvitado = {
               // Handle login as guest
            },
            onNavUp = {
               navController.popBackStack()
            }
         )
      }



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

