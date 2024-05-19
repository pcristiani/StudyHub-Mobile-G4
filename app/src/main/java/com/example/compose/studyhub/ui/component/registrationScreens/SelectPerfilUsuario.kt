package com.example.compose.studyhub.ui.component.registrationScreens

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.studyhub.R
import com.example.compose.studyhub.ui.component.registrarEstudiante.QuestionWrapper

@Composable
fun SelectPerfilUsuario(
   @StringRes
   titleResourceId: Int,
   @StringRes
   directionsResourceId: Int,
   possibleAnswers: List<Int>,
   selectedAnswers: List<Int>,
   onOptionSelected: (selected: Boolean, answer: Int) -> Unit,
   modifier: Modifier = Modifier,
                       ) {
   QuestionWrapper(
      modifier = modifier, titleResourceId = titleResourceId,
      directionsResourceId = directionsResourceId,
                  ) {
      possibleAnswers.forEach {
         val selected = selectedAnswers.contains(it)
         CheckboxRow(modifier = Modifier.padding(vertical = 8.dp), text = stringResource(id = it), selected = selected, onOptionSelected = { onOptionSelected(!selected, it) })
      }
   }
}


@Composable
fun CheckboxRow(text: String, selected: Boolean, onOptionSelected: () -> Unit, modifier: Modifier = Modifier) {
   Surface(shape = MaterialTheme.shapes.small, color = if (selected) {
      MaterialTheme.colorScheme.primaryContainer
   } else {
      MaterialTheme.colorScheme.surface
   }, border = BorderStroke(width = 1.dp, color = if (selected) {
      MaterialTheme.colorScheme.primary
   } else {
      MaterialTheme.colorScheme.outline
   }), modifier = modifier
      .clip(MaterialTheme.shapes.small)
      .clickable(onClick = onOptionSelected)) {
      Row(modifier = Modifier
         .fillMaxWidth()
         .padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
         Text(text, Modifier.weight(1f), style = MaterialTheme.typography.bodyLarge)
         Box(Modifier.padding(8.dp)) { Checkbox(selected, onCheckedChange = null) }
      }
   }
}




@Preview
@Composable
fun SelectPerfilUsuarioPreview() {
   val possibleAnswers = listOf(R.string.txt_estudiante)
   val selectedAnswers = remember { mutableStateListOf(R.string.txt_estudiante) }
   SelectPerfilUsuario(titleResourceId = R.string.txt_question, directionsResourceId = R.string.select_rol, possibleAnswers = possibleAnswers, selectedAnswers = selectedAnswers, onOptionSelected = { _, _ -> })
}
