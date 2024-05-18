package com.example.compose.studyhub.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.compose.studyhub.ui.theme.ThemeStudyHub
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(drawerState: DrawerState) {
   val scope = rememberCoroutineScope()
   val navController = rememberNavController()
   CenterAlignedTopAppBar(title = { Text(text = "StudyHub") }, navigationIcon = {
      IconButton(onClick = {
         println("TopBar")
         scope.launch {
            drawerState.open()
         }
      }) {
         Icon(Icons.Outlined.Menu, "Menu")
      }
   })
}

@Preview
@Composable
fun TopBarPreview() {
   ThemeStudyHub {/*  TopBar(inscripcionScreen())
      TopBar(NovedadesScreen())*/
   }

}
