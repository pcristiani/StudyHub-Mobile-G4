package com.example.compose.studyhub

import LoginRequest
import RetrofitClient
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.compose.studyhub.auth.decodeJWT
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
import com.example.compose.studyhub.ui.screen.LoginRegisterScreen
import com.example.compose.studyhub.ui.screen.LoginScreen
import com.example.compose.studyhub.ui.theme.ThemeStudyHub
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

///
// * MainActivity --> Clase principal de la aplicación.
// * AppCompatActivity --> Clase base para actividades.

class MainActivity: AppCompatActivity() {




   override fun onCreate(savedInstanceState: Bundle?) {



      enableEdgeToEdge()
      super.onCreate(savedInstanceState)


      setContent{
         ThemeStudyHub {
            val navController = rememberNavController()
            SetupNavGraph(navController = navController)
         }
      }
   }
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
            onNavigateToRegister = { ci ->
               navController.navigate("loginScreen/$ci")
                                   },
            onLoginInvitado = {
               navController.navigate("homeScreen")
            })
      }


      composable(NavRoutes.LoginScreen) {
         val ci = it.arguments?.getString("ci")

         LoginRoute(
            ci = ci,
            onLoginSubmitted = {
               navController.navigate("screenNovedades")
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
               // Handle login submitted action
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

