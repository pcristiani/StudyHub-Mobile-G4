package com.example.compose.studyhub.ui.component.questionLogin

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.compose.studyhub.R
import com.example.compose.studyhub.ui.component.register.SelectAvatarUsuario
import com.example.compose.studyhub.ui.component.register.SelectFechaUsuario
import com.example.compose.studyhub.ui.component.register.SelectPerfilUsuario
import com.example.compose.studyhub.ui.component.register.Superhero

///
@Composable
fun FreeTimeQuestion(
   selectedAnswers: List<Int>,
   onOptionSelected: (selected: Boolean, answer: Int) -> Unit,
   modifier: Modifier = Modifier,
) {
   SelectPerfilUsuario(
      titleResourceId = R.string.txt_question,
      directionsResourceId = R.string.select_rol,
      possibleAnswers = listOf(
         R.string.txt_estudiante,
      ),
      selectedAnswers = selectedAnswers,
      onOptionSelected = onOptionSelected,
      modifier = modifier,
   )
}

///
@Composable
fun SuperheroQuestion(
   selectedAnswer: Superhero?,
   onOptionSelected: (Superhero) -> Unit,
   modifier: Modifier = Modifier,
) {
   SelectAvatarUsuario(
      titleResourceId = R.string.txt_question2,
      directionsResourceId = R.string.select_one,
      possibleAnswers = listOf(
         Superhero(R.string.txt_steve, R.drawable.spark),
         Superhero(R.string.txt_camila, R.drawable.lenz),
         Superhero(R.string.txt_lucas, R.drawable.bug_of_chaos),
         Superhero(R.string.txt_studente, R.drawable.a5_student1_128),
         Superhero(R.string.txt_harry, R.drawable.a23_harrypotter_128),
         Superhero(R.string.txt_estudia, R.drawable.a8_student3_128),
         Superhero(R.string.txt_cat, R.drawable.a17_rat_128),
         Superhero(R.string.txt_perro, R.drawable.a30_dog_128),
         Superhero(R.string.txt_payaso, R.drawable.a18_joker_128),
         Superhero(R.string.txt_alien, R.drawable.a21_alien_128),
      ),
      selectedAnswer = selectedAnswer,
      onOptionSelected = onOptionSelected,
      modifier = modifier,
   )
}

///
@Composable
fun TakeawayQuestion(
   dateInMillis: Long?,
   onClick: () -> Unit,
   modifier: Modifier = Modifier,
) {
   SelectFechaUsuario(
      titleResourceId = R.string.txt_question3,
      directionsResourceId = R.string.select_fecha,
      dateInMillis = dateInMillis,
      onClick = onClick,
      modifier = modifier,
   )
} // @Composable
// fun FeelingAboutSelfiesQuestion(
//         value: Float?,
//         onValueChange: (Float) -> Unit,
//         modifier: Modifier = Modifier,
// ) {
//     SliderQuestion(
//             titleResourceId = R.string.txt_question4,
//             value = value,
//             onValueChange = onValueChange,
//             startTextResource = R.string.strongly_dislike,
//             neutralTextResource = R.string.neutral,
//             endTextResource = R.string.strongly_like,
//             modifier = modifier,
//     )
// }
// @Composable
// fun TakeSelfieQuestion(
//         imageUri: Uri?,
//         getNewImageUri: () -> Uri,
//         onPhotoTaken: (Uri) -> Unit,
//         modifier: Modifier = Modifier,
// ) {
//     PhotoQuestion(
//             titleResourceId = R.string.txt_question4,
//             imageUri = imageUri,
//             getNewImageUri = getNewImageUri,
//             onPhotoTaken = onPhotoTaken,
//             modifier = modifier,
//     )
// }
