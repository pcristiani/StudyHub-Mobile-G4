package com.example.compose.studyhub.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.studyhub.ui.theme.ThemeStudyHub
import com.example.compose.studyhub.ui.theme.md_theme_List

@Composable
fun HorarioCard(nombre: String, selected: Boolean = false, onHeaderClicked: () -> Unit) {
  Card(modifier = Modifier
    .fillMaxWidth()
    .clickable { onHeaderClicked() }
    .padding(horizontal = 22.dp, vertical = 3.dp),// elevation = 4.dp,
    border = BorderStroke(1.1.dp, md_theme_List.copy(alpha = 0.6f)), shape = MaterialTheme.shapes.large
    ) {
    //colors = CardColors(MaterialTheme.colorScheme.secondary, Color.Black, Color.Black, Color.White

    Box(modifier = Modifier.padding(18.dp)) {
      Text(text = nombre, style = MaterialTheme.typography.labelLarge, textAlign = MaterialTheme.typography.bodyLarge.textAlign)
      println("selected: $nombre")
    }
  }
}

@Preview
@Composable
fun HorarioCardPreview() {
  ThemeStudyHub {
    HorarioCard("Horario", selected = true) {}
  }
}