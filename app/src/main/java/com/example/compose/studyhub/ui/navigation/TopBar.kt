package com.example.compose.studyhub.ui.navigation

import alertDialogDoc
import alertDialogDoc2
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.SupervisorAccount
import androidx.compose.material.icons.outlined.SwitchAccount
import androidx.compose.material.icons.rounded.AccountBox
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.SwitchAccount
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api


import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImagePainter.State.Empty.painter
import com.example.compose.studyhub.R
import com.example.compose.studyhub.ui.theme.ThemeStudyHub
import com.example.compose.studyhub.ui.theme.md_theme_light_outline
import com.example.compose.studyhub.ui.theme.md_theme_light_shadow
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavHostController,drawerState: DrawerState) {
   val scope = rememberCoroutineScope()
   TasksTopAppBar(
      navController = navController,
      openDrawer = {
         scope.launch {
            drawerState.open()
         }
      },
      drawerState = drawerState,
      onClearCompletedTasks = {},
      onRefresh = {}
   )
   //TopAppBarSample()
   /*  TasksTopAppBar(
       openDrawer = {
          scope.launch {
             drawerState.open()
          }
       },
       onClearCompletedTasks = {},
       onRefresh = {}
    ) */
}

@Composable
fun ReplyProfileImage(drawableResource: Int, description: String, modifier: Modifier = Modifier) {
   Icon(
      modifier = modifier
         .size(32.dp),
      imageVector = Icons.Outlined.AccountCircle,
      contentDescription = description
   )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksTopAppBar(navController: NavHostController,
   drawerState: DrawerState,
   openDrawer: () -> Unit, onClearCompletedTasks: () -> Unit, onRefresh: () -> Unit
) {
   TopAppBar(
      title = { Text(text = stringResource(id = R.string.app_name)) },
      navigationIcon = {
         IconButton(onClick = openDrawer) {
            Icon(Icons.Filled.Menu, contentDescription = stringResource(id = R.string.txt_admin))
         }

      },
      actions = {
         MoreTasksMenu(navController,drawerState, onClearCompletedTasks, onRefresh)
      },
      modifier = Modifier.fillMaxWidth()
   )
}

@Composable
fun MoreTasksMenu(
   navController: NavController, drawerState:DrawerState, onClearCompletedTasks: () -> Unit, onRefresh: () -> Unit
) {
   val scope = rememberCoroutineScope()
   val showLogoutDialog = remember { mutableStateOf(false) }
   TopAppBarDropdownMenu(
      iconContent = {
         ReplyProfileImage(drawableResource = R.drawable.account_circle_32, description = stringResource(id = R.string.txt_close))
      }
   ) { closeMenu ->
      DropdownMenuItem(onClick = {
         onClearCompletedTasks()
         closeMenu()
         try {
            navController.navigate(NavRoutes.EditarPerfilScreen)
         } catch (e: Exception) {
            println("Error al navegar: ${e.message}")
         }
      }) {
         Text(text = stringResource(id = R.string.txt_editPerfil))
      }
      DropdownMenuItem(
         onClick = {
            scope.launch {
               drawerState.close()
            }
            showLogoutDialog.value = true
         }, modifier = Modifier
      ) {
         Text(text = stringResource(id = R.string.logout))
      }
   }
}


@Composable
fun TopAppBarDropdownMenu(iconContent: @Composable () -> Unit, content: @Composable ColumnScope.(() -> Unit) -> Unit) {
   var expanded by remember { mutableStateOf(false) }

   Box(modifier = Modifier.wrapContentSize(Alignment.TopEnd)) {
      IconButton(onClick = { expanded = !expanded }) {
         iconContent()
      }
      DropdownMenu(
         expanded = expanded,
         onDismissRequest = { expanded = false },
         modifier = Modifier.wrapContentSize(Alignment.TopEnd)
      ) {
         content { expanded = false }
      }
   }
}


@Preview
@Composable
fun MainScreenPreview() {
   ThemeStudyHub {
      TopBar(navController = rememberNavController(),drawerState = rememberDrawerState(DrawerValue.Closed))
      // TopAppBarSample()
   }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarSamples() {
   TopAppBar(
      navigationIcon = {
         IconButton(onClick = { /*TODO*/ }) {
            Icon(imageVector = Icons.Rounded.Menu, contentDescription = null)
         }
      },
      title = { Text(text = "Sample Title") },
      actions = {
         IconButton(onClick = { /*TODO*/ }) {
            Icon(
               imageVector = Icons.Rounded.Search,
               contentDescription = null
            )
         }
         IconButton(onClick = { /*TODO*/ }) {
            Icon(
               imageVector = Icons.Outlined.AccountCircle,
               contentDescription = null
            )
         }
      }
   )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarSample() {
   val navController = rememberNavController()
   TopAppBar(
      navigationIcon = {
         IconButton( onClick = {

            try {
               navController.navigate(NavRoutes.EditarPerfilScreen) //  println(item.ruta)
            } catch (e: Exception) {
               println("Error al navegar: ${e.message}")
            }
         }, modifier = Modifier
         ) {
            Icon(
               imageVector = Icons.Rounded.Menu, contentDescription = "Settings", modifier = Modifier.size(60.dp, 60.dp), tint = colorResource(
                  id = R.color.darker_gray                                                                                                                                                      )
            )
         }
         //   onClick = { /*TODO*/ })
         /*    {
               Icon(imageVector = Icons.Rounded.Menu, contentDescription = null)
            } */
      },
      title = { Text(text = "Sample Title") },
      actions = {
         IconButton(onClick = { /*TODO*/ }) {
            Icon(
               imageVector = Icons.Rounded.Search,
               contentDescription = null
            )
         }
         IconButton(onClick = { /*TODO*/ }) {
            Icon(
               imageVector = Icons.Outlined.AccountCircle,
               contentDescription = null
            )
         }
      }
   )
   /*   SearchBarComponent(
        emails = LocalEmailsDataProvider.allEmails,
        modifier = Modifier,
        navigateToDetail = { _, _ -> }
     ) */
}
/* @Composable
fun MainScreen() {
   val navController = rememberNavController()
   val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

   Scaffold(
      topBar = { TopBar(drawerState = drawerState) },
      drawerContent = {  *//* Contenido del cajón *//*  },
   ) {
      NavHost(navController = navController, startDestination = "homeScreen") {
         composable("homeScreen") { HomeScreen(navController) }
         composable("editarPerfilScreen") { EditarPerfilScreen(navController) }
         // Añade otras pantallas aquí
      }
   }
} */
