package com.example.compose.studyhub.ui.screen.estudiante

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.compose.studyhub.R
import com.example.compose.studyhub.data.UserRepository
import com.example.compose.studyhub.http.requests.getCarrerasRequest
import com.example.compose.studyhub.ui.component.Logo
import com.example.compose.studyhub.ui.navigation.LogoutBox
import com.example.compose.studyhub.ui.theme.md_theme_dark_text

@Composable
fun NovedadesScreen(navController: NavHostController): DrawerState {
   val showLogoutDialog = remember { mutableStateOf(false) }

   val greetings = "Bienvenido, ${UserRepository.getNombre()}"

   Column(modifier = Modifier.fillMaxSize().padding(bottom=200.dp, start=0.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
      //Image(painter = painterResource(id = R.drawable.logotext), modifier = Modifier.size(220.dp), contentDescription = "Logo")
      //Text("Novedades", style = MaterialTheme.typography.titleMedium, color = md_theme_dark_text)
      Column(modifier = Modifier.wrapContentHeight(align = Alignment.CenterVertically)) {
         Logo(modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .size(240.dp)
            .padding(top = 76.dp))

         Text(text = greetings, style = MaterialTheme.typography.headlineLarge, textAlign = TextAlign.Center, modifier = Modifier
            .padding(top = 30.dp)
            .fillMaxWidth())
      }
   }


   BackHandler{
      showLogoutDialog.value = true
   }

   if (showLogoutDialog.value) {
      LogoutBox(navController = navController, onDismiss = { showLogoutDialog.value = false })
   }


   return DrawerState(DrawerValue.Closed)
}


@Preview
@Composable
fun NovedadesScreenPreview() {
   //NovedadesScreen()
}


