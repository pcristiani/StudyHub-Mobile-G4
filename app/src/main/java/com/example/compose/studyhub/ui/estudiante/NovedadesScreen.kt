package com.example.compose.studyhub.ui.estudiante

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.compose.studyhub.R
import com.example.compose.studyhub.ui.theme.md_theme_dark_text

@Composable
fun NovedadesScreen(): DrawerState {
   val navController = rememberNavController()
   Column(horizontalAlignment = Alignment.CenterHorizontally) {
      Image(painter = painterResource(id = R.drawable.logotext), modifier = Modifier.size(220.dp), contentDescription = "Logo")
      Text("Novedades", style = MaterialTheme.typography.titleMedium, color = md_theme_dark_text)
   }
   return DrawerState(DrawerValue.Closed)
}


@Preview
@Composable
fun NovedadesScreenPreview() {
   NovedadesScreen()
}


