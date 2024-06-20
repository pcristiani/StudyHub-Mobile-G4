package com.example.compose.studyhub.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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

@Composable
fun CarreraCard(nombre: String) {
  Card(modifier = Modifier
    .fillMaxWidth()
    .padding(horizontal = 25.dp, vertical = 5.dp), // elevation = 10.dp,
    // backgroundColor = Color.White,
    shape = RoundedCornerShape(10.dp)) {
    Row(modifier = Modifier
      .fillMaxWidth()
      .padding(6.dp), verticalAlignment = Alignment.CenterVertically) {
      Image(painter = painterResource(id = R.drawable.educacion), contentDescription = null, modifier = Modifier
        .size(40.dp)
        .clip(MaterialTheme.shapes.extraSmall)
        .padding(start = 2.dp, end = 2.dp))
      Spacer(Modifier.width(10.dp))

      Text(nombre, Modifier.weight(0.1f), style = MaterialTheme.typography.bodyLarge)
      Box(Modifier.padding(10.dp))
    }
  }
}

@Preview
@Composable
fun CarreraCardPreview() {
  ThemeStudyHub {
    CarreraCard("Tecnologo Informatica")
  }
}