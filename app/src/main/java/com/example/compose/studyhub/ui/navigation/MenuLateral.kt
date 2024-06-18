package com.example.compose.studyhub.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.compose.studyhub.Destinations
import com.example.compose.studyhub.MainActivity
import com.example.compose.studyhub.R
import com.example.compose.studyhub.StudyHubNavHost
import com.example.compose.studyhub.data.UserRepository
import com.example.compose.studyhub.http.requests.cerrarSesionRequest
import com.example.compose.studyhub.ui.component.DialogBoxCreation
import com.example.compose.studyhub.ui.navigation.ItemsMenuLateral.ItemMenuGestion
import com.example.compose.studyhub.ui.navigation.ItemsMenuLateral.ItemMenuInscripcion
import com.example.compose.studyhub.ui.navigation.ItemsMenuLateral.ItemMenuNovedades
import com.example.compose.studyhub.ui.navigation.ItemsMenuLateral.ItemMenuPlanEstudios
import com.example.compose.studyhub.ui.navigation.ItemsMenuLateral.ItemMenuSolicitudes
import com.example.compose.studyhub.ui.route.LogoutRoute
import com.example.compose.studyhub.ui.theme.ThemeStudyHub
import kotlinx.coroutines.launch

@Composable
fun MenuLateral(navController: NavHostController, drawerState: DrawerState, contenido: @Composable () -> Unit) {
   val scope = rememberCoroutineScope()
   val menuItems = listOf(ItemMenuNovedades, ItemMenuPlanEstudios, ItemMenuInscripcion, ItemMenuSolicitudes, ItemMenuGestion)
   val showLogoutDialog = remember { mutableStateOf(false) }

   ModalNavigationDrawer(drawerState = drawerState, drawerContent = {
      ModalDrawerSheet { //  Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
         Column(modifier = Modifier.padding(end = 5.dp, top = 5.dp, bottom = 5.dp)) {
            HeaderMenuLateral(topAppBarText = stringResource(id = R.string.app_name), onNavUp = {
               scope.launch {
                  drawerState.close()
               }
            })/*   Image(painter = painterResource(id = R.drawable.logotext), contentDescription = "Logo", modifier = Modifier.size(150.dp), alignment = Alignment.Center)*/
            menuItems.forEach { item ->
               NavigationDrawerItem(icon = { Icon(item.icon, null) }, label = { Text(text = item.title) }, selected = currentRoute(navController) == item.ruta, onClick = {
                  scope.launch {
                     drawerState.close()
                  }
                  try {
                     navController.navigate(item.ruta) //  println(item.ruta)
                  } catch (e: Exception) {
                     println("Error al navegar: ${e.message}")
                  }
               })
            }
            Spacer(modifier = Modifier.weight(1f))
            Row(){
               Box(modifier = Modifier
                  .size(70.dp, 80.dp)
                  .padding(bottom = 10.dp, start = 20.dp)

               ){
                  IconButton(
                     onClick = {scope.launch {
                        drawerState.close()
                        }
                        showLogoutDialog.value = true

                               },
                     modifier = Modifier.align(Alignment.CenterStart)
                  ){
                     Icon(imageVector = Icons.AutoMirrored.Filled.Logout, contentDescription = "Settings", modifier=Modifier.size(70.dp, 80.dp), tint = colorResource(
                        id = R.color.darker_gray
                     ))
                  }
               }

               Box(modifier = Modifier
                  .size(70.dp, 80.dp)
                  .padding(bottom = 10.dp, start = 20.dp)

               ){
                  IconButton(
                     onClick = {scope.launch {
                        drawerState.close()
                     }
                        try {
                           navController.navigate(NavRoutes.EditarPerfilScreen) //  println(item.ruta)
                        } catch (e: Exception) {
                           println("Error al navegar: ${e.message}")
                        }},
                     modifier = Modifier.align(Alignment.CenterStart)
                  ){
                     Icon(imageVector = Icons.Filled.Build, contentDescription = "Settings", modifier=Modifier.size(70.dp, 80.dp), tint = colorResource(
                             id = R.color.darker_gray
                             ))
                  }
               }
            }
            if (showLogoutDialog.value) {
               LogoutBox(navController = navController, onDismiss = { showLogoutDialog.value = false })
            }
         }
      }




   }) { contenido() }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeaderMenuLateral(topAppBarText: String, onNavUp: () -> Unit) {
   CenterAlignedTopAppBar(
      title = {
         Text(text = topAppBarText, modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center))
      },
      navigationIcon = {
         IconButton(onClick = onNavUp) {
            Icon(imageVector = Icons.Filled.ChevronLeft, contentDescription = stringResource(id = R.string.back), tint = MaterialTheme.colorScheme.secondary)
         }
      },
      actions = { Spacer(modifier = Modifier.width(68.dp)) }, )
}

@Composable
fun LogoutBox(navController: NavHostController, onDismiss: () -> Unit){

   LogoutRoute(
      onConfirmation = {

         println("Acá estoy")
         navController.navigate(Destinations.INICIO_ROUTE) {
            popUpTo(navController.graph.startDestinationId) {
               saveState = true
            }
            launchSingleTop = true
            restoreState = true
         }

         onDismiss()
      },
      dialogTitle = stringResource(id = R.string.Logout_title),
      dialogText = stringResource(id = R.string.Logout_question),
      onDismiss =  onDismiss
   )
}


@Preview
@Composable
fun MenuLateralPreview() {
   ThemeStudyHub {
      val navController = rememberNavController()
      MenuLateral(navController, drawerState = DrawerState(DrawerValue.Open)) {}
   }
}