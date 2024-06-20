package com.example.compose.studyhub.questionLogin

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class) class QuestionViewModelTest {
   private lateinit var viewModel: QuestionViewModel

   @Before
   fun setUp() {
      viewModel = QuestionViewModel(PhotoUriManager(ApplicationProvider.getApplicationContext()))
   }

   @Test
   fun onFreeTimeResponse() {
      val answerOne = 0
      val answerTwo = 99 // comienza vacío, siguiente deshabilitado
      Truth.assertThat(viewModel.freeTimeResponse).isEmpty()
      Truth.assertThat(viewModel.isNextEnabled).isFalse() // Agregar dos valores arbitrarios
      viewModel.onFreeTimeResponse(true, answerOne)
      viewModel.onFreeTimeResponse(true, answerTwo)
      Truth.assertThat(viewModel.freeTimeResponse).containsExactly(answerOne, answerTwo)
      Truth.assertThat(viewModel.isNextEnabled).isTrue() // Eliminar un valor
      viewModel.onFreeTimeResponse(false, answerTwo)
      Truth.assertThat(viewModel.freeTimeResponse).containsExactly(answerOne)
      Truth.assertThat(viewModel.isNextEnabled).isTrue() // Eliminar el último valor
      viewModel.onFreeTimeResponse(false, answerOne)
      Truth.assertThat(viewModel.freeTimeResponse).isEmpty()
      Truth.assertThat(viewModel.isNextEnabled).isFalse()
   }
}
