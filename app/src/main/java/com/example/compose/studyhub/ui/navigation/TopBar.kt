package com.example.compose.studyhub.ui.navigation

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
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
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
import androidx.navigation.compose.rememberNavController
import com.example.compose.studyhub.R
import com.example.compose.studyhub.ui.theme.ThemeStudyHub
import com.example.compose.studyhub.ui.theme.md_theme_light_shadow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(drawerState: DrawerState) {
   val scope = rememberCoroutineScope()
   val navController = rememberNavController()
   
   TasksTopAppBar(openDrawer = {
      scope.launch {
         drawerState.open()
      }
   }, onFilterAllTasks = {}, onFilterActiveTasks = {}, onFilterCompletedTasks = {}, onClearCompletedTasks = {}, onRefresh = {})/*  CenterAlignedTopAppBar(title = { Text(text = "StudyHub") }, navigationIcon = {
      IconButton(onClick = {
         println("TopBar")
         scope.launch {
            drawerState.open()
         }
      }) {
         Icon(Icons.Outlined.Menu, "Menu")
      }
   }) */
}


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
fun TasksTopAppBar(openDrawer: () -> Unit, onFilterAllTasks: () -> Unit, onFilterActiveTasks: () -> Unit, onFilterCompletedTasks: () -> Unit, onClearCompletedTasks: () -> Unit, onRefresh: () -> Unit) {
   TopAppBar(title = { Text(text = stringResource(id = R.string.app_name)) }, navigationIcon = {
      IconButton(onClick = openDrawer) {
         Icon(Icons.Filled.Menu, stringResource(id = R.string.txt_admin))
      }
   }, actions = {
      FilterTasksMenu(onFilterAllTasks, onFilterActiveTasks, onFilterCompletedTasks)
      MoreTasksMenu(onClearCompletedTasks, onRefresh)
   }, modifier = Modifier.fillMaxWidth())
}


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
private fun MoreTasksMenu(onClearCompletedTasks: () -> Unit, onRefresh: () -> Unit) {
   TopAppBarDropdownMenu(iconContent = { //   Icon(Icons.Filled.AccountCircle, stringResource(id = R.string.txt_continue))
      ReplyProfileImage(drawableResource = R.drawable.steve_256, stringResource(id = R.string.txt_close))
   }) { closeMenu ->
      DropdownMenuItem(onClick = { onClearCompletedTasks(); closeMenu() }) {
         Text(text = stringResource(id = R.string.txt_editPerfil))
      }
      DropdownMenuItem(onClick = { onRefresh(); closeMenu() }) {
         Text(text = stringResource(id = R.string.txt_close))
      }
   }
}


@Composable
private fun TopAppBarDropdownMenu(iconContent: @Composable () -> Unit, content: @Composable ColumnScope.(() -> Unit) -> Unit) {
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


@Preview
@Composable
fun TopBarPreview() {
   ThemeStudyHub {
      TopBar(drawerState = DrawerState(DrawerValue.Closed))
   }
}

