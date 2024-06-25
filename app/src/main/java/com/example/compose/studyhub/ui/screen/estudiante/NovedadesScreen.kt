package com.example.compose.studyhub.ui.screen.estudiante

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.compose.studyhub.data.UserRepository
import com.example.compose.studyhub.ui.component.Logo
import com.example.compose.studyhub.ui.navigation.LogoutBox
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun NovedadesScreen(navController: NavHostController): DrawerState {
   val showLogoutDialog = remember { mutableStateOf(false) }
   val nombre = UserRepository.getNombre()?: ""

   val greetings = "Bienvenido, $nombre"

   var displayText by remember {mutableStateOf("")}
   val scope = rememberCoroutineScope()

   Column(modifier = Modifier.fillMaxSize().padding(bottom=200.dp, start=0.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
      //Image(painter = painterResource(id = R.drawable.logotext), modifier = Modifier.size(220.dp), contentDescription = "Logo")
      //Text("Novedades", style = MaterialTheme.typography.titleMedium, color = md_theme_dark_text)


      //Efecto para el logo
      val transition = rememberInfiniteTransition(label = "")
      val animatedScale by transition.animateFloat(
         initialValue = 1f,
         targetValue = 1.1f,
         animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
         ), label = ""
      )

      //Efecto para el texto
      LaunchedEffect(greetings) {
         if(nombre!=""){
            scope.launch {
               val stringBuilder = StringBuilder()
               for (letter in greetings) {
                  stringBuilder.append(letter)
                  delay(70)
                  displayText = stringBuilder.toString()
               }
            }
         }

      }


      Column(modifier = Modifier.wrapContentHeight(align = Alignment.CenterVertically)) {
         Logo(modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .size(240.dp)
            .scale(animatedScale)
            .padding(top = 76.dp))





         Text(text = displayText, style = MaterialTheme.typography.headlineLarge, textAlign = TextAlign.Center, modifier = Modifier
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
   val uiScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

   fun runOnUiThread(block: suspend () -> Unit) = uiScope.launch { block() }

   return DrawerState(DrawerValue.Closed)
}



@Preview
@Composable
fun NovedadesScreenPreview() {
   //NovedadesScreen()
}


