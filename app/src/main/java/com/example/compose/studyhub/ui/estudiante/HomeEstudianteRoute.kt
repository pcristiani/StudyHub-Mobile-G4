package com.example.compose.studyhub.ui.estudiante

import android.content.Context
import android.content.ContextWrapper
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.compose.studyhub.SetupNavGraph

private const val CONTENT_ANIMATION_DURATION = 300 // /

@Composable
fun HomeEstudianteRoute(
   email: String?,
   onQuestionComplete: () -> Unit,
) {
   val navController = rememberNavController()
   SetupNavGraph(navController = navController) //  val viewModel: QuestionViewModel = viewModel()
   //  val surveyScreenData = viewModel.surveyScreenData ?: return
   /*BackHandler {
      if (!viewModel.onBackPressed()) {   onNavUp()
      }
   }
  QuestionQuestionsScreen(surveyScreenData = surveyScreenData, isNextEnabled = viewModel.isNextEnabled, onClosePressed = { *//*onNavUp()*//* }, onPreviousPressed = { viewModel.onPreviousPressed() }, onNextPressed = { viewModel.onNextPressed() }, onDonePressed = { viewModel.onDonePressed(onQuestionComplete) }) { paddingValues ->
      val modifier = Modifier.padding(paddingValues)

      AnimatedContent(targetState = surveyScreenData, transitionSpec = {
         val animationSpec: TweenSpec<IntOffset> = tween(CONTENT_ANIMATION_DURATION)
         val direction = getTransitionDirection(
            initialIndex = initialState.questionIndex,
            targetIndex = targetState.questionIndex,
         )

         slideIntoContainer(
            towards = direction,
            animationSpec = animationSpec,
         ) togetherWith slideOutOfContainer(towards = direction, animationSpec = animationSpec)
      }, label = "surveyScreenDataAnimation") { targetState ->
         when (targetState.surveyQuestion) {
            usrQuestion.USER_ROLE -> {
               FreeTimeQuestion(
                  selectedAnswers = viewModel.freeTimeResponse,
                  onOptionSelected = viewModel::onFreeTimeResponse,
                  modifier = modifier,
               )
            }

            usrQuestion.AVATAR_USER -> AvatarQuestion(
               selectedAnswer = viewModel.avatarResponse,
               onOptionSelected = viewModel::onAvatarResponse,
               modifier = modifier,
            )

            usrQuestion.BIRTH_DATE -> {
               val supportFragmentManager = LocalContext.current.findActivity().supportFragmentManager
               SeleccionarFecha(
                  dateInMillis = viewModel.fechaResponse,
                  onClick = {
                     selectDate(date = viewModel.fechaResponse, supportFragmentManager = supportFragmentManager, onDateSelected = viewModel::onFechaResponse)
                  },
                  modifier = modifier,
               )
            }
         }
}  }
}
 ////private fun getTransitionDirection(initialIndex: Int, targetIndex: Int): AnimatedContentTransitionScope.SlideDirection {
   return if (targetIndex > initialIndex) {
      AnimatedContentTransitionScope.SlideDirection.Left // Avanzar
   } else {
      AnimatedContentTransitionScope.SlideDirection.Right //  back
   }
}
 ///

private fun selectDate(
   date: Long?,
   supportFragmentManager: FragmentManager,
   onDateSelected: (date: Long) -> Unit,
) {
   val picker = MaterialDatePicker.Builder.datePicker().setSelection(date).build()
   picker.show(supportFragmentManager, picker.toString())
   picker.addOnPositiveButtonClickListener { picker.selection?.let { onDateSelected(it) } }
}
   */
}

///
private tailrec fun Context.findActivity(): AppCompatActivity = when (this) {
   is AppCompatActivity -> this
   is ContextWrapper -> this.baseContext.findActivity()
   else -> throw IllegalArgumentException("Could not find activity!")
}
