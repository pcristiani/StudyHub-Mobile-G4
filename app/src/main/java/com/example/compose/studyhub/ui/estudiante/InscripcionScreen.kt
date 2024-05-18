package com.example.compose.studyhub.ui.estudiante

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.studyhub.R

@Composable
fun inscripcionScreen(): DrawerState {
   Column(modifier = Modifier.fillMaxSize(),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally) {
      Image(painter = painterResource(id = R.drawable.logotext),modifier = Modifier.size(260.dp), contentDescription = "Logo")
   }
   return DrawerState(DrawerValue.Closed)
}

@Preview
@Composable
fun InscripcionScreenPreview() {
   inscripcionScreen()
}
