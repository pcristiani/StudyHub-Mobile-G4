package com.example.compose.studyhub.ui.component.registrarEstudiante

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.compose.studyhub.R
import com.example.compose.studyhub.ui.component.registrationScreens.Avatar
import com.example.compose.studyhub.ui.component.registrationScreens.SelectAvatarUsuario
import com.example.compose.studyhub.ui.component.registrationScreens.SelectFechaUsuario
import com.example.compose.studyhub.ui.component.registrationScreens.SelectPerfilUsuario

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
fun AvatarQuestion(
   selectedAnswer: Avatar?,
   onOptionSelected: (Avatar) -> Unit,
   modifier: Modifier = Modifier,
) {
   SelectAvatarUsuario(
      titleResourceId = R.string.txt_question2,
      directionsResourceId = R.string.select_one,
      possibleAnswers = listOf(
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
      ),
      selectedAnswer = selectedAnswer,
      onOptionSelected = onOptionSelected,
      modifier = modifier,
   )
}

///
@Composable
fun SeleccionarFecha(
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
} // @Composable // fun FeelingAboutSelfiesQuestion( //         value: Float?, //         onValueChange: (Float) -> Unit, //         modifier: Modifier = Modifier, // ) {
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
