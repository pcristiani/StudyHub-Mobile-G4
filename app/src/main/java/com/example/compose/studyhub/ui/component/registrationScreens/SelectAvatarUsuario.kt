package com.example.compose.studyhub.ui.component.registrationScreens

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
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
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.studyhub.R
import com.example.compose.studyhub.ui.component.registrarEstudiante.QuestionWrapper

@Composable
fun SelectAvatarUsuario(
   @StringRes titleResourceId: Int,
   @StringRes directionsResourceId: Int,
   possibleAnswers: List<Avatar>,
   selectedAnswer: Avatar?,
   onOptionSelected: (Avatar) -> Unit,
   modifier: Modifier = Modifier,
) {
   QuestionWrapper(
      titleResourceId = titleResourceId,
      directionsResourceId = directionsResourceId,
      modifier = modifier.selectableGroup(),
   ) {
      possibleAnswers.forEach {
         val selected = it == selectedAnswer
         RadioButtonWithImageRow(modifier = Modifier.padding(vertical = 8.dp), text = stringResource(id = it.stringResourceId), imageResourceId = it.imageResourceId, selected = selected, onOptionSelected = { onOptionSelected(it) })
      }
   }
}

@Composable
fun RadioButtonWithImageRow(
   text: String,
   @DrawableRes imageResourceId: Int,
   selected: Boolean,
   onOptionSelected: () -> Unit,
   modifier: Modifier = Modifier,
) {
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
      .selectable(selected, onClick = onOptionSelected, role = Role.RadioButton)) {
      Row(modifier = Modifier
         .fillMaxWidth()
         .padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
         Image(painter = painterResource(id = imageResourceId), contentDescription = null, modifier = Modifier
            .size(56.dp)
            .clip(MaterialTheme.shapes.extraSmall)
            .padding(start = 0.dp, end = 8.dp))
         Spacer(Modifier.width(8.dp))

         Text(text, Modifier.weight(1f), style = MaterialTheme.typography.bodyLarge)
         Box(Modifier.padding(8.dp)) { RadioButton(selected, onClick = null) }
      }
   }
}

@Preview
@Composable
fun SelectAvatarUsuarioPreview() {
   val possibleAnswers = listOf(
      Avatar(R.string.txt_steve, R.drawable.spark),
      Avatar(R.string.txt_camila, R.drawable.lenz),
      Avatar(R.string.txt_seba, R.drawable.celebridad_512),
      Avatar(R.string.txt_lucas, R.drawable.bug_of_chaos),
      Avatar(R.string.txt_harry, R.drawable.a23_harrypotter_128),
      Avatar(R.string.txt_robot, R.drawable.a16_robot_128),
      Avatar(R.string.txt_experta_soft, R.drawable.a33_developer),
      Avatar(R.string.txt_experto_soft, R.drawable.a34_developer1_128),
      Avatar(R.string.txt_genio, R.drawable.a1_boy_128),
      Avatar(R.string.txt_genia, R.drawable.a28_nerd_128),
      Avatar(R.string.txt_estudiante, R.drawable.a6_student2),
      Avatar(R.string.txt_studente, R.drawable.a5_student1_128),
      Avatar(R.string.txt_cat, R.drawable.a17_rat_128),
      Avatar(R.string.txt_perro, R.drawable.a30_dog_128),
      Avatar(R.string.txt_payaso, R.drawable.a18_joker_128),
      Avatar(R.string.txt_alien, R.drawable.a21_alien_128),
   )
   var selectedAnswer by remember { mutableStateOf<Avatar?>(null) }

   SelectAvatarUsuario(
      titleResourceId = R.string.txt_question4,
      directionsResourceId = R.string.select_one,
      possibleAnswers = possibleAnswers,
      selectedAnswer = selectedAnswer,
      onOptionSelected = { selectedAnswer = it },
   )
}

data class Avatar(@StringRes val stringResourceId: Int, @DrawableRes val imageResourceId: Int)
