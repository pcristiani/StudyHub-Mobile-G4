package com.example.compose.studyhub.ui.component.loginRegisterEdit

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.studyhub.R
import com.example.compose.studyhub.data.User
import com.example.compose.studyhub.data.UserRepository
import com.example.compose.studyhub.domain.reformatDate
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun Birthday(birthdayState: DatePickerState, imeAction: ImeAction = ImeAction.Next, onImeAction: () -> Unit = {}) {
  Text(text = stringResource(id = R.string.birthday), style = MaterialTheme.typography.bodyMedium)
  Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
    val showDatePicker = remember { mutableStateOf(false) }
    val selectedDate = remember { mutableStateOf<Date?>(null) } // this will store whatever date the user selects // Button to show the date picker dialog
    val sdf = SimpleDateFormat("MMM dd,yyyy", Locale.getDefault())
    val currentDate = Date(System.currentTimeMillis())
    Button(onClick = { showDatePicker.value = true }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(5.dp), colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.white), contentColor = colorResource(R.color.text_black)), border = BorderStroke(1.dp, colorResource(R.color.darker_gray))) {
      if (UserRepository.user == User.NoUserLoggedIn) {
        val displayDate = selectedDate.value ?: currentDate
        Text(text = sdf.format(displayDate), style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(top=10.dp, bottom=10.dp))
      } else {
        val fechaNacimiento = UserRepository.getFechaNacimiento()
        if (fechaNacimiento != null) {
          Text(text = reformatDate(fechaNacimiento), style = MaterialTheme.typography.bodyMedium)
        }
      }
    }

    if (showDatePicker.value) {
      DatePickerDialog(onDismissRequest = { showDatePicker.value = false }, confirmButton = {
        TextButton(onClick = {
          showDatePicker.value = false
          selectedDate.value = Date(birthdayState.selectedDateMillis ?: 0)
        }, enabled = birthdayState.selectedDateMillis != null) {
          Text(text = "Confirm")
        }
      }, dismissButton = {
        TextButton(onClick = { showDatePicker.value = false }) {
          Text(text = "Dismiss")
        }
      }) {
        DatePicker(state = birthdayState)
      }
    }
  }
}

