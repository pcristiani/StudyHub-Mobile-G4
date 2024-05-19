package com.example.compose.studyhub.ui.component.registrarEstudiante

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.compose.studyhub.ui.component.registrationScreens.Avatar

const val simpleDateFormatPattern = "EEE, MMM d"


///
class QuestionViewModel(): ViewModel() {
   
   private val questionOrder: List<usrQuestion> = listOf(usrQuestion.USER_ROLE, usrQuestion.AVATAR_USER, usrQuestion.BIRTH_DATE)
   private var questionIndex = 0
   
   
   // ----- Expuestas como estado-----
   private val _freeTimeResponse = mutableStateListOf<Int>()
   val freeTimeResponse: List<Int>
      get() = _freeTimeResponse
   private val _avatarResponse = mutableStateOf<Avatar?>(null)
   val avatarResponse: Avatar?
      get() = _avatarResponse.value
   private val _fechaResponse = mutableStateOf<Long?>(null)
   val fechaResponse: Long?
      get() = _fechaResponse.value
   private val _feelingAboutSelfiesResponse = mutableStateOf<Float?>(null)
   val feelingAboutSelfiesResponse: Float?
      get() = _feelingAboutSelfiesResponse.value
   
   
   // ----- Expuesto como State -----
   private val _surveyScreenData = mutableStateOf(createQuestionScreenData())
   val surveyScreenData: QuestionScreenData?
      get() = _surveyScreenData.value
   private val _isNextEnabled = mutableStateOf(false)
   val isNextEnabled: Boolean
      get() = _isNextEnabled.value
   
   
   // Devuelve true si el Modelo View Manye  regresó una  pregunta
   fun onBackPressed(): Boolean {
      if (questionIndex == 0) {
         return false
      }
      changeQuestion(questionIndex - 1)
      return true
   }
   
   fun onPreviousPressed() {
      if (questionIndex == 0) {
         throw IllegalStateException("onPreviousPressed when on question 0")
      }
      changeQuestion(questionIndex - 1)
   }
   
   fun onNextPressed() {
      changeQuestion(questionIndex + 1)
   }
   
   private fun changeQuestion(newQuestionIndex: Int) {
      questionIndex = newQuestionIndex
      _isNextEnabled.value = getIsNextEnabled()
      _surveyScreenData.value = createQuestionScreenData()
   }
   
   fun onDonePressed(onQuestionComplete: () -> Unit) {
      onQuestionComplete()
   }
   
   fun onFreeTimeResponse(selected: Boolean, answer: Int) {
      if (selected) {
         _freeTimeResponse.add(answer)
      } else {
         _freeTimeResponse.remove(answer)
      }
      _isNextEnabled.value = getIsNextEnabled()
   }
   
   fun onAvatarResponse(avatar: Avatar) {
      _avatarResponse.value = avatar
      _isNextEnabled.value = getIsNextEnabled()
   }
   
   fun onFechaResponse(timestamp: Long) {
      _fechaResponse.value = timestamp
      _isNextEnabled.value = getIsNextEnabled()
   }
   
   fun onFeelingAboutSelfiesResponse(feeling: Float) {
      _feelingAboutSelfiesResponse.value = feeling
      _isNextEnabled.value = getIsNextEnabled()
   }
   
   private fun getIsNextEnabled(): Boolean {
      return when (questionOrder[questionIndex]) {
         usrQuestion.USER_ROLE -> _freeTimeResponse.isNotEmpty()
         usrQuestion.AVATAR_USER -> _avatarResponse.value != null
         usrQuestion.BIRTH_DATE -> _fechaResponse.value != null
      }
   }
   
   private fun createQuestionScreenData(): QuestionScreenData {
      return QuestionScreenData(
         questionIndex = questionIndex,
         questionCount = questionOrder.size,
         shouldShowPreviousButton = questionIndex > 0,
         shouldShowDoneButton = questionIndex == questionOrder.size - 1,
         surveyQuestion = questionOrder[questionIndex],
                               )
   }
}


///
enum class usrQuestion {
   
   USER_ROLE, AVATAR_USER, BIRTH_DATE
}


///
data class QuestionScreenData(val questionIndex: Int, val questionCount: Int, val shouldShowPreviousButton: Boolean, val shouldShowDoneButton: Boolean, val surveyQuestion: usrQuestion)


