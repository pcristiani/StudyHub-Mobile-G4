package com.example.compose.studyhub.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.studyhub.ui.theme.ThemeStudyHub
import com.example.compose.studyhub.ui.theme.md_theme_List


@Composable
fun CarreraCard(nombre: String, selected: Boolean = false, onHeaderClicked: () -> Unit) {
  Card(modifier = Modifier
    .fillMaxWidth()
    .clickable { onHeaderClicked() }
    .padding(horizontal = 20.dp, vertical = 6.dp),
    border = BorderStroke(1.dp, md_theme_List.copy(alpha = 0.5f)), shape = RoundedCornerShape(8.dp)) {
    Box(modifier = Modifier.padding(18.dp)) {
     Text(text = nombre, style = MaterialTheme.typography.labelLarge )
    }
  }
}
// Text(text = nombre, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface.copy(alpha = stronglyDeemphasizedAlpha), textAlign = TextAlign.Center, modifier = Modifier.padding(top = 100.dp, bottom = 6.dp))

@Preview
@Composable
fun CarreraCardPreview() {
  ThemeStudyHub {
    CarreraCard("Tecnologo Informatica", selected = true, onHeaderClicked = {})
  }
}