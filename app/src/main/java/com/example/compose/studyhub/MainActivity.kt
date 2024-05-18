package com.example.compose.studyhub

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.compose.studyhub.ui.estudiante.NovedadesScreen
import com.example.compose.studyhub.ui.estudiante.inscripcionScreen
import com.example.compose.studyhub.ui.navigation.MenuLateral
import com.example.compose.studyhub.ui.navigation.TopBar
import com.example.compose.studyhub.ui.theme.NavigationDemoTheme
import com.example.compose.studyhub.ui.theme.ThemeStudyHub
import kotlinx.coroutines.launch

///
// * MainActivity --> Clase principal de la aplicación.
// * AppCompatActivity --> Clase base para actividades.
class MainActivity : AppCompatActivity() {
   override fun onCreate(savedInstanceState: Bundle?) {
      enableEdgeToEdge()

      super.onCreate(savedInstanceState)

      setContent {
         ThemeStudyHub { //     StudyHubNavHost()
         }

         NavigationDemoTheme {
            val navController = rememberNavController()
            SetupNavGraph(navController = navController)
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

      composable("screenSolicitudes") { //  TopBar(SolicitudesScreen())
         MenuLateral(navController, drawerState, contenido = {
            ScreenSolicitudes(navController, drawerState)
         })
      }

      composable("screenInscripciones") {
         MenuLateral(navController, drawerState, contenido = {
            ScreenInscripciones(navController, drawerState)
         })
      }
   }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ScreenNovedades(navController: NavHostController, drawerState: DrawerState) {
   Scaffold(topBar = {
      TopBar(drawerState)
   }) {
      Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
         Image(painter = painterResource(id = R.drawable.logotext), modifier = Modifier.size(260.dp), contentDescription = "Logo")
      }

      Column(modifier = Modifier
         .fillMaxSize()
         .padding(10.dp), verticalArrangement = Arrangement.Bottom) {
         Button(onClick = { navController.navigate("screenInscripciones") }, modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)) {
            Text("Ir a Inscripciones")
         }
      }
   }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ScreenEstudios(navController: NavHostController, drawerState: DrawerState) {
   val scope = rememberCoroutineScope()

   Scaffold(topBar = {
      TopAppBar(title = { Text("Plan de Estudios") }, navigationIcon = {
         IconButton(onClick = {
            scope.launch {
               drawerState.open()
            }
         }) {
            Icon(Icons.Outlined.Menu, "Menu")
         }
      })
   }) {
      Column(modifier = Modifier
         .fillMaxSize()
         .padding(10.dp), verticalArrangement = Arrangement.Bottom) {
         Button(onClick = { navController.navigate("screenInscripciones") }, modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)) {
            Text("Ir a Inscripciones otro")
         }
      }
   }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ScreenSolicitudes(navController: NavHostController, drawerState: DrawerState) {
   val scope = rememberCoroutineScope()

   Scaffold(topBar = {
      TopAppBar(title = { Text("Solicitudes") }, navigationIcon = {
         IconButton(onClick = {
            scope.launch {
               drawerState.open()
            }
         }) {
            Icon(Icons.Outlined.Menu, "Menu")
         }
      })
   }) {
      Column(modifier = Modifier
         .fillMaxSize()
         .padding(10.dp), verticalArrangement = Arrangement.Bottom) {
         Button(onClick = { navController.navigate("screenInscripciones") }, modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)) {
            Text("Ir a a otro")
         }
      }
   }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenInscripciones(navController: NavHostController, drawerState: DrawerState) {
   val scope = rememberCoroutineScope()

   Scaffold(topBar = {
      TopAppBar(title = { Text("Inscripciones") }, navigationIcon = {
         IconButton(onClick = {
            scope.launch {
               drawerState.open()
            }
         }) {
            Icon(Icons.Outlined.Menu, "Menu")
         }
      })
   }) {}
}/*TOPBAR*//*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(drawerState: DrawerState) {
   val scope = rememberCoroutineScope()
   CenterAlignedTopAppBar(title = { Text(text = "StudyHub") }, navigationIcon = {
      IconButton(onClick = {
         scope.launch {
            drawerState.open()
         }
      }) { Icon(Icons.Outlined.Menu, "Menu") }
   })
}
*/

@Preview
@Composable
fun TopBarPreview() {
   ThemeStudyHub { //  TopBar(inscripcionScreen())
      TopBar(inscripcionScreen())
      TopBar(NovedadesScreen())
   }

}



