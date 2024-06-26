package com.example.compose.studyhub.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
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
fun AsignaturaCard(nombre: String) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 15.dp, vertical = 3.dp),
    border = BorderStroke(1.1.dp, md_theme_List.copy(alpha = 0.6f)), shape = MaterialTheme.shapes.large) {

    Box(modifier = Modifier.padding(18.dp)) {
      Text(text = nombre, style = MaterialTheme.typography.labelLarge ) //   }
      println("selected: $nombre")
    }
    //elevation = 10.dp,
    //backgroundColor = Color.White,
/*     shape = RoundedCornerShape(10.dp) */
     /*  Text(
        text = nombre,
        modifier = Modifier.padding(start= 20.dp, top = 15.dp, bottom = 15.dp),
        style = MaterialTheme.typography.bodyLarge
      ) */
     // Text(text = nombre, style = MaterialTheme.typography.labelLarge )
      /*Spacer(modifier = Modifier.padding(start = 100.dp))
      Text(
          text = nombre,
          modifier = Modifier.padding(start= 20.dp, top = 15.dp, bottom = 15.dp),
          style = MaterialTheme.typography.labelSmall,
          textAlign = TextAlign.End
      )*/
    //}

  }
}

@Preview
@Composable
fun AsignaturaCardPreview() {
  ThemeStudyHub {
    AsignaturaCard("Asignatura")
  }
}