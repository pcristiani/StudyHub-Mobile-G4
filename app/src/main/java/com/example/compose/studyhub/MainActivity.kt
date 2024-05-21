package com.example.compose.studyhub

import LoginRequest
import LoginResponse
import RetrofitClient
import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
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
import com.example.compose.studyhub.ui.navigation.NavRoutes
import com.example.compose.studyhub.ui.navigation.TopBar
import com.example.compose.studyhub.ui.theme.ThemeStudyHub
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.compose.studyhub.auth.decodeJWT

///
// * MainActivity --> Clase principal de la aplicación.
// * AppCompatActivity --> Clase base para actividades.
/*
class MainActivity: AppCompatActivity() {
   
   override fun onCreate(savedInstanceState: Bundle?) {
      enableEdgeToEdge()
      super.onCreate(savedInstanceState)
      
      setContent {
         ThemeStudyHub { // StudyHubNavHost() //
            val navController = rememberNavController()
            SetupNavGraph(navController = navController)
         }
      }
   }
}
*/


class MainActivity: AppCompatActivity() {
   
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)/*   setContentView(R.layout.activity_main) */
      val loginRequest = LoginRequest("52275944", "GCBBp79Kz3Mr")
      
      RetrofitClient.api.login(loginRequest).enqueue(object: Callback<LoginResponse> {
         override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
            if (response.isSuccessful) {
               val loginResponse = response.body() // Procesar la respuesta del login

               val decodedResponse = decodeJWT(loginResponse!!.token)

               //println("$decodedResponse")
               Toast.makeText(this@MainActivity, "Token: ${loginResponse?.token}", Toast.LENGTH_SHORT).show()
            } else {
               Toast.makeText(this@MainActivity, "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
            }
         }
         
         override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
            Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            println("Error: ${t.message}")
         }
      })
   }
}


@Composable
fun SetupNavGraph(navController: NavHostController) {
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

