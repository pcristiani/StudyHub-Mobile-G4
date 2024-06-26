package com.example.compose.studyhub.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.studyhub.R
import com.example.compose.studyhub.ui.theme.ThemeStudyHub
import com.example.compose.studyhub.ui.theme.md_theme_List

@Composable
fun CarreraCard(nombre: String, selected: Boolean = false, onHeaderClicked: () -> Unit) { // Column {
  Card(modifier = Modifier
    .fillMaxWidth()
    .clickable { onHeaderClicked() }
    .padding(4.dp), // elevation = 4.dp,
    border = BorderStroke(1.4.dp, md_theme_List.copy(alpha = 0.22f)), shape = MaterialTheme.shapes.large) {
    Box(modifier = Modifier.padding(14.dp)) {
      Text(text = nombre, style = MaterialTheme.typography.bodyLarge ) //   }
      println("selected: $nombre")
    }
  }
}

@Preview
@Composable
fun CarreraCardPreview() {
  ThemeStudyHub {
    CarreraCard("Tecnologo Informatica", selected = false, onHeaderClicked = {})
  }
}