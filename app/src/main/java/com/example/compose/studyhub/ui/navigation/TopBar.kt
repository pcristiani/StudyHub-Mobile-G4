package com.example.compose.studyhub.ui.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.compose.studyhub.R
import com.example.compose.studyhub.ui.theme.OwlTheme
import com.example.compose.studyhub.ui.theme.ThemeStudyHub
import com.example.compose.studyhub.ui.theme.md_theme_light_shadow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(drawerState: DrawerState) {
   val navController = rememberNavController()
   val scope = rememberCoroutineScope()
   TasksTopAppBar(openDrawer = {
      scope.launch {
         drawerState.open()
      }
   }, navController = navController, onClearCompletedTasks = {}, onRefresh = {})
}
/* fun TopBar(onboardingComplete: DrawerState) {
   Row(
      horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically, modifier = Modifier
         .fillMaxWidth()
         .statusBarsPadding()
      ) {
      Image(
         painter = painterResource(id = OwlTheme.images.lockupLogo), contentDescription = null, modifier = Modifier.padding(16.dp)
           )
      androidx.compose.material.IconButton(modifier = Modifier.padding(16.dp), onClick = { onboardingComplete }) {
         androidx.compose.material.Icon(
            imageVector = Icons.Filled.AccountCircle,
            contentDescription = stringResource(R.string.txt_admin)
       )
      }
   }
} */

@Composable
fun ReplyProfileImage(drawableResource: Int, description: String, modifier: Modifier = Modifier) {
   Image(
      modifier = modifier
         .size(45.dp)
         .clip(CircleShape)
         .padding(4.dp)
         .border(1.dp, md_theme_light_shadow, CircleShape),
      painter = painterResource(id = drawableResource),
      contentDescription = description,
        )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksTopAppBar(navController: NavHostController, openDrawer: () -> Unit,  onClearCompletedTasks: () -> Unit, onRefresh: () -> Unit) {
   TopAppBar(title = { Text(text = stringResource(id = R.string.app_name)) }, navigationIcon = {
      IconButton(onClick = openDrawer) {
         Icon(Icons.Filled.Menu, stringResource(id = R.string.txt_admin))
      }
   }, actions = {
      MoreTasksMenu(navController, onClearCompletedTasks, onRefresh)
   }, modifier = Modifier.fillMaxWidth())
}


@Composable
 fun MoreTasksMenu(navController: NavHostController, onClearCompletedTasks: () -> Unit, onRefresh: () -> Unit) {
   TopAppBarDropdownMenu(iconContent = { //   Icon(Icons.Filled.AccountCircle, stringResource(id = R.string.txt_continue))
      ReplyProfileImage(drawableResource = R.drawable.steve_256, stringResource(id = R.string.txt_close))
   }){} /*{ closeMenu ->
      DropdownMenuItem(onClick = {
         onClearCompletedTasks()
         closeMenu()
         try{
            navController.navigate(NavRoutes.EditarPerfilScreen)
         }catch (e: Exception){
            println("Error al navegar: ${e.message}")
         }
      }) {
         Text(text = stringResource(id = R.string.txt_editPerfil))
      }
      DropdownMenuItem(onClick = { onRefresh(); closeMenu()}) {
         Text(text = stringResource(id = R.string.txt_close))
      }
   }*/
}


@Composable
fun TopAppBarDropdownMenu(iconContent: @Composable () -> Unit, content: @Composable ColumnScope.(() -> Unit) -> Unit) {
   var expanded by remember { mutableStateOf(false) }
   
   Box(modifier = Modifier.wrapContentSize(Alignment.TopEnd)) {
      IconButton(onClick = { expanded = !expanded }) {
         iconContent()
      }
      DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }, modifier = Modifier.wrapContentSize(Alignment.TopEnd)) {
         content { expanded = !expanded }
      }
   }
}

/*
@Composable
private fun FilterTasksMenu(onFilterAllTasks: () -> Unit, onFilterActiveTasks: () -> Unit, onFilterCompletedTasks: () -> Unit) {
   TopAppBarDropdownMenu(iconContent = {}) { closeMenu ->
      DropdownMenuItem(onClick = { onFilterAllTasks(); closeMenu() }) {
         Text(text = stringResource(id = R.string.txt_admin))
      }
      DropdownMenuItem(onClick = { onFilterActiveTasks(); closeMenu() }) {
         Text(text = stringResource(id = R.string.txt_funcionario))
      }
      DropdownMenuItem(onClick = { onFilterCompletedTasks(); closeMenu() }) {
         Text(text = stringResource(id = R.string.txt_continue))
      }
   }
}

@Composable
fun MessageCard() { // Add padding around our message
   Row(modifier = Modifier.padding(all = 8.dp)) {
      Image(painter = painterResource(R.drawable.ic_selfie_dark), contentDescription = "Contact profile picture", modifier = Modifier // Set image size to 40 dp
         .size(40.dp) // Clip image to be shaped as a circle
         .clip(CircleShape)) // Add a horizontal space between the image and the column
      Spacer(modifier = Modifier.width(8.dp))
      
      Column {
         Text(text = "msg.author") // Add a vertical space between the author and message texts
         Spacer(modifier = Modifier.height(4.dp))
         Text(text = "msg.body")
      }
   }
}
*/


@Preview
@Composable
fun TopBarPreview() {
   ThemeStudyHub {
      TopBar(DrawerState())
   }
}

fun DrawerState(): DrawerState {
   
   return DrawerState()
}

