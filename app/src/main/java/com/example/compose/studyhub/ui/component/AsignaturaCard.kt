package com.example.compose.studyhub.ui.component

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


@Composable
fun AsignaturaCard(nombre: String) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 25.dp, vertical = 5.dp),
    //elevation = 10.dp,
    //backgroundColor = Color.White,
    shape = RoundedCornerShape(10.dp)
  ) {
    Row(modifier = Modifier.fillMaxWidth()){
      Text(
        text = nombre,
        modifier = Modifier.padding(start= 20.dp, top = 15.dp, bottom = 15.dp),
        style = MaterialTheme.typography.labelSmall
      )
      /*Spacer(modifier = Modifier.padding(start = 100.dp))
      Text(
          text = nombre,
          modifier = Modifier.padding(start= 20.dp, top = 15.dp, bottom = 15.dp),
          style = MaterialTheme.typography.labelSmall,
          textAlign = TextAlign.End
      )*/
    }

  }
}

@Preview
@Composable
fun AsignaturaCardPreview() {
  ThemeStudyHub {
    AsignaturaCard("Asignatura")
  }
}