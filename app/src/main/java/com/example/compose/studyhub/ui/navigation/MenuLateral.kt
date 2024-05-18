package com.example.compose.studyhub.ui.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.compose.studyhub.ui.navigation.ItemsMenuLateral.ItemMenuGestion
import com.example.compose.studyhub.ui.navigation.ItemsMenuLateral.ItemMenuInscripcion
import com.example.compose.studyhub.ui.navigation.ItemsMenuLateral.ItemMenuNovedades
import com.example.compose.studyhub.ui.navigation.ItemsMenuLateral.ItemMenuPlanEstudios
import com.example.compose.studyhub.ui.navigation.ItemsMenuLateral.ItemMenuSolicitudes
import com.example.compose.studyhub.ui.theme.ThemeStudyHub
import kotlinx.coroutines.launch

@Composable
fun MenuLateral(navController: NavHostController, drawerState: DrawerState, contenido: @Composable () -> Unit) {
   val scope = rememberCoroutineScope()
   val menuItems = listOf(ItemMenuNovedades, ItemMenuPlanEstudios, ItemMenuInscripcion, ItemMenuSolicitudes, ItemMenuGestion)/*  val onNavUp: () -> Unit = {
      scope.launch { //  navController.navigate("screenNovedades")
         drawerState.open()
      }
   } */ //println("MENU LATERAL")
   ModalNavigationDrawer(drawerState = drawerState, drawerContent = {
      ModalDrawerSheet { //  Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
         Column(modifier = Modifier.padding(start = 5.dp, end = 1.dp, top = 1.dp, bottom = 1.dp)) {/* Image(painter = painterResource(id = R.drawable.logotext), contentDescription = "Logo", modifier = Modifier.size(150.dp), alignment = androidx.compose.ui.Alignment.Center)*//*   LoginRegisterTopAppBar(topAppBarText = stringResource(id = R.string.app_name), onNavUp = onNavUp)*/

            menuItems.forEach { item ->
               NavigationDrawerItem(icon = { Icon(item.icon, null) }, label = { Text(text = item.title) }, selected = currentRoute(navController) == item.ruta, onClick = {
                  scope.launch {
                     drawerState.close()
                  }

                  try {
                     navController.navigate(item.ruta)/*     drawerState.close()*/ //navController.navigate("screenNovedades")
                     println(item.ruta)
                  } catch (e: Exception) {
                     println("Error al navegar: ${e.message}")/*}*/
                  }
               })
            }
         }
      }
   }) { contenido() }
}

@Preview
@Composable
fun MenuLateralPreview() {
   ThemeStudyHub {/*  MenuLateral(navController = NavHostController(context = LocalContext.current), drawerState = DrawerState(DrawerValue.Closed)) {
         Column {
            InscripcionScreenPreview()
         }
      }*//*    TopBar(inscripcionScreen())
      LoginRegisterTopAppBar(topAppBarText = stringResource(id = R.string.sign_in), onNavUp = { ItemMenuGestion })*/
   }
}