package com.example.compose.studyhub.ui.estudiante

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import com.example.compose.studyhub.R
import com.example.compose.studyhub.ui.theme.md_theme_dark_text

@Composable
fun GestionScreen(): DrawerState {
   Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
      Image(painter = painterResource(id = R.drawable.a19_dj_128), modifier = Modifier.size(120.dp), contentDescription = "Logo")
      Text("Gestion", style = MaterialTheme.typography.titleMedium, color = md_theme_dark_text)
   }
   return DrawerState(DrawerValue.Closed)
}


@Preview
@Composable
fun GestionScreenPreview() {
   GestionScreen()
}
