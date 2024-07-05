package com.example.compose.studyhub.ui.component.loginRegisterEdit

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.studyhub.R
import com.example.compose.studyhub.data.User
import com.example.compose.studyhub.data.UserRepository
import com.example.compose.studyhub.domain.reformatDate
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.util.Date
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun Birthday(birthdayState: DatePickerState, imeAction: ImeAction = ImeAction.Next, onImeAction: () -> Unit = {}) {
  Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
    val showDatePicker = remember { mutableStateOf(false) }
    val selectedDate = remember { mutableStateOf<Date?>(null) }
    val sdf = SimpleDateFormat("dd MMM,yyyy", Locale.getDefault())
    var displayDate: String = ""

    displayDate = if (UserRepository.user == User.NoUserLoggedIn) {
      selectedDate.value?.let { sdf.format(it) } ?:""
    } else {
      if(selectedDate.value == null){
        UserRepository.getFechaNacimiento()?.let { reformatDate(it) }?:""
      }
      else{
        selectedDate.value?.let { sdf.format(it) } ?:""
      }
    }


    OutlinedTextField(value = displayDate, onValueChange={}, readOnly= true, label = {
      Text(text = stringResource(id = R.string.birthday), style = MaterialTheme.typography.bodyMedium)

    }, modifier = Modifier.fillMaxWidth().onFocusChanged { focusState -> if(focusState.isFocused){showDatePicker.value = true } })

    if (showDatePicker.value) {
      DatePickerDialog(onDismissRequest = { showDatePicker.value = false }, confirmButton = {
        TextButton(onClick = {
          showDatePicker.value = false
          birthdayState.selectedDateMillis?.let {
            val instant = Instant.ofEpochMilli(it)
            val zonedDateTime = instant.atZone(ZoneId.systemDefault())
            selectedDate.value = Date.from(zonedDateTime.toInstant())
          } ?: run {
            selectedDate.value = null
          }
        }, enabled = birthdayState.selectedDateMillis != null) {
          Text(text = "Confirmar")
        }
      }, dismissButton = {
        TextButton(onClick = { showDatePicker.value = false }) {
          Text(text = "Cancelar")
        }
      }) {
        DatePicker(state = birthdayState)
      }
    }
  }
}

