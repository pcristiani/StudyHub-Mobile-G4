package com.example.compose.studyhub.ui.component.questionLogin

import android.content.res.Configuration
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides.Companion.Bottom
import androidx.compose.foundation.layout.WindowInsetsSides.Companion.Horizontal
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.studyhub.R
import com.example.compose.studyhub.data.UserRepository
import com.example.compose.studyhub.ui.theme.ThemeStudyHub
import com.example.compose.studyhub.ui.theme.stronglyDeemphasizedAlpha
import com.example.compose.studyhub.util.supportWideScreen

///
@Composable
fun QuestionQuestionsScreen(
   surveyScreenData: QuestionScreenData,
   isNextEnabled: Boolean,
   onClosePressed: () -> Unit,
   onPreviousPressed: () -> Unit,
   onNextPressed: () -> Unit,
   onDonePressed: () -> Unit,
   content: @Composable (PaddingValues) -> Unit,
) {
   Surface(modifier = Modifier.supportWideScreen()) {
      Scaffold(topBar = {
         QuestionTopAppBar(
            questionIndex = surveyScreenData.questionIndex,
            totalQuestionsCount = surveyScreenData.questionCount,
            onClosePressed = onClosePressed,
         )
      }, content = content, bottomBar = {
         QuestionBottomBar(shouldShowPreviousButton = surveyScreenData.shouldShowPreviousButton, shouldShowDoneButton = surveyScreenData.shouldShowDoneButton, isNextButtonEnabled = isNextEnabled, onPreviousPressed = onPreviousPressed, onNextPressed = onNextPressed, onDonePressed = onDonePressed)
      })
   }
} ///

@Composable
fun QuestionResultScreen(onDonePressed: () -> Unit) {
   println("---> QuestionResultScreen")
   val u = UserRepository.user.toString();
   val partes = u.split("=")
   val strEmail = partes[1].dropLast(1)

   Surface(modifier = Modifier.supportWideScreen()) {
      Scaffold(content = { innerPadding ->
         val modifier = Modifier.padding(innerPadding)
         QuestionResult(title = stringResource(R.string.result_login), subtitle = strEmail, description = stringResource(R.string.survey_result_description), modifier = modifier)
      }, bottomBar = {
         OutlinedButton(onClick = onDonePressed, modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 30.dp)) { Text(text = stringResource(id = R.string.txt_continue)) }
      })
   }
} ///

@Composable
private fun QuestionResult(title: String, subtitle: String, description: String, modifier: Modifier = Modifier) {
   LazyColumn(modifier = modifier.fillMaxSize()) {
      item {
         Spacer(modifier = Modifier.height(44.dp))
         Text(text = title, style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(horizontal = 20.dp))
         Text(text = subtitle, style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(20.dp))
      }
   }
}

///
@Composable
private fun TopAppBarTitle(questionIndex: Int, totalQuestionsCount: Int, modifier: Modifier = Modifier) {
   Row(modifier = modifier) {
      Text(text = (questionIndex + 1).toString(), style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurface.copy(alpha = stronglyDeemphasizedAlpha))
      Text(text = stringResource(R.string.question_count, totalQuestionsCount), style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f))
   }
}

///
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionTopAppBar(questionIndex: Int, totalQuestionsCount: Int, onClosePressed: () -> Unit) {
   Column(modifier = Modifier.fillMaxWidth()) {
      CenterAlignedTopAppBar(title = {
         TopAppBarTitle(
            questionIndex = questionIndex,
            totalQuestionsCount = totalQuestionsCount,
         )
      }, actions = {
         IconButton(onClick = onClosePressed, modifier = Modifier.padding(4.dp)) {
            Icon(Icons.Filled.Close, contentDescription = stringResource(id = R.string.close), tint = MaterialTheme.colorScheme.onSurface.copy(stronglyDeemphasizedAlpha))
         }
      })
      val animatedProgress by animateFloatAsState(targetValue = (questionIndex + 1) / totalQuestionsCount.toFloat(), animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec)
      LinearProgressIndicator(
         progress = animatedProgress,
         modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
         trackColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
      )
   }
}

///
@Composable
fun QuestionBottomBar(shouldShowPreviousButton: Boolean, shouldShowDoneButton: Boolean, isNextButtonEnabled: Boolean, onPreviousPressed: () -> Unit, onNextPressed: () -> Unit, onDonePressed: () -> Unit) {
   Surface(shadowElevation = 7.dp) {
      Row(modifier = Modifier
         .fillMaxWidth() // implementar componentes de Material3
         .windowInsetsPadding(WindowInsets.systemBars.only(Horizontal + Bottom))
         .padding(horizontal = 16.dp, vertical = 20.dp)) {
         if (shouldShowPreviousButton) {
            OutlinedButton(modifier = Modifier
               .weight(1f)
               .height(48.dp), onClick = onPreviousPressed) { Text(text = stringResource(id = R.string.previous)) }
            Spacer(modifier = Modifier.width(16.dp))
         }
         if (shouldShowDoneButton) {
            Button(
               modifier = Modifier
                  .weight(1f)
                  .height(48.dp),
               onClick = onDonePressed,
               enabled = isNextButtonEnabled,
            ) { Text(text = stringResource(id = R.string.done)) }
         } else {
            Button(
               modifier = Modifier
                  .weight(1f)
                  .height(48.dp),
               onClick = onNextPressed,
               enabled = isNextButtonEnabled,
            ) { Text(text = stringResource(id = R.string.next)) }
         }
      }
   }
}

/*@Preview(name = "Sign in dark theme", uiMode = UI_MODE_NIGHT_YES)*/
@Preview(name = "Sign in light theme", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun LoginPreview() {
   ThemeStudyHub { QuestionResultScreen(onDonePressed = {}) }
}