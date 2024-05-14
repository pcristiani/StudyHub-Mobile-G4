package com.example.compose.studyhub.ui.component.questionLogin

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.compose.studyhub.ui.component.register.Superhero

const val simpleDateFormatPattern = "EEE, MMM d"

///
class QuestionViewModel() : ViewModel() {
   private val questionOrder: List<usrQuestion> = listOf(
      usrQuestion.USER_ROLE,
      usrQuestion.AVATAR_USER,
      usrQuestion.BIRTH_DATE,
       // usrQuestion.PREGUNTA_4,
      //    usrQuestion.TAKE_SELFIE,
   )
   private var questionIndex = 0

   // ----- Expuestas como estado-----
   private val _freeTimeResponse = mutableStateListOf<Int>()
   val freeTimeResponse: List<Int>
      get() = _freeTimeResponse
   private val _superheroResponse = mutableStateOf<Superhero?>(null)
   val superheroResponse: Superhero?
      get() = _superheroResponse.value
   private val _takeawayResponse = mutableStateOf<Long?>(null)
   val takeawayResponse: Long?
      get() = _takeawayResponse.value
   private val _feelingAboutSelfiesResponse = mutableStateOf<Float?>(null)
   val feelingAboutSelfiesResponse: Float?
      get() = _feelingAboutSelfiesResponse.value // private val _selfieUri = mutableStateOf<Uri?>(null)
   // val selfieUri
   //     get() = _selfieUri.value
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

   fun onSuperheroResponse(superhero: Superhero) {
      _superheroResponse.value = superhero
      _isNextEnabled.value = getIsNextEnabled()
   }

   fun onTakeawayResponse(timestamp: Long) {
      _takeawayResponse.value = timestamp
      _isNextEnabled.value = getIsNextEnabled()
   }

   fun onFeelingAboutSelfiesResponse(feeling: Float) {
      _feelingAboutSelfiesResponse.value = feeling
      _isNextEnabled.value = getIsNextEnabled()
   } // fun onSelfieResponse(uri: Uri) {
   //     _selfieUri.value = uri
   //     _isNextEnabled.value = getIsNextEnabled()
   // }
   /*   fun getNewSelfieUri() = photoUriManager.buildNewUri()*/
   private fun getIsNextEnabled(): Boolean {
      return when (questionOrder[questionIndex]) {
         usrQuestion.USER_ROLE -> _freeTimeResponse.isNotEmpty()
         usrQuestion.AVATAR_USER -> _superheroResponse.value != null
         usrQuestion.BIRTH_DATE -> _takeawayResponse.value != null // usrQuestion.PREGUNTA_4 -> _feelingAboutSelfiesResponse.value != null
         //  usrQuestion.TAKE_SELFIE -> _selfieUri.value != null
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
   USER_ROLE, AVATAR_USER, BIRTH_DATE, // PREGUNTA_4, //  TAKE_SELFIE,
}

///
data class QuestionScreenData(
   val questionIndex: Int,
   val questionCount: Int,
   val shouldShowPreviousButton: Boolean,
   val shouldShowDoneButton: Boolean,
   val surveyQuestion: usrQuestion,
) ///
/*class QuestionViewModelFactory(private val photoUriManager: PhotoUriManager) :
        ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuestionViewModel::class.java)) {
            return QuestionViewModel(photoUriManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}*/
