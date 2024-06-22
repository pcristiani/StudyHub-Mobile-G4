package com.example.compose.studyhub.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.studyhub.R
import com.example.compose.studyhub.data.User
import com.example.compose.studyhub.data.UserRepository
import com.example.compose.studyhub.domain.CIState
import com.example.compose.studyhub.domain.EmailState
import com.example.compose.studyhub.domain.TextFieldState
import com.example.compose.studyhub.domain.datePicker
import com.example.compose.studyhub.domain.reformatDate
import com.example.compose.studyhub.ui.theme.ThemeStudyHub
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

///
// LoginRegisterScreen muestra la pantalla de inicio de sesión y registro
@Composable
fun LoginRegisterScreen(modifier: Modifier = Modifier, contentPadding: PaddingValues = PaddingValues(), content: @Composable () -> Unit) {
   LazyColumn(modifier = modifier, contentPadding = contentPadding) {
      item {
         Spacer(modifier = Modifier.height(44.dp))
         Box(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)) { content() }
         Spacer(modifier = Modifier.height(16.dp))
      }
   }
}


///
// LoginRegisterTopAppBar muestra la barra superior de la pantalla de inicio de sesión y registro
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginRegisterTopAppBar(topAppBarText: String, onNavUp: () -> Unit) {
   CenterAlignedTopAppBar(
      title = {
         Text(text = topAppBarText, modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center))
      },
      navigationIcon = {
         IconButton(onClick = onNavUp) {
            Icon(imageVector = Icons.Filled.ChevronLeft, contentDescription = stringResource(id = R.string.back), tint = MaterialTheme.colorScheme.primary)
         }
      },
      actions = { Spacer(modifier = Modifier.width(68.dp)) },
                         )
}

@Composable
fun Name(textState: TextFieldState = remember { TextFieldState() }, imeAction: ImeAction = ImeAction.Next, onImeAction: () -> Unit = {}) {
   OutlinedTextField(value = textState.text, onValueChange = { textState.text = it }, label = {
      Text(text = stringResource(id = R.string.name), style = MaterialTheme.typography.bodyMedium)
   }, modifier = Modifier
      .fillMaxWidth()
      .onFocusChanged { focusState ->
         textState.onFocusChange(focusState.isFocused)

         if (!focusState.isFocused) {
            textState.enableShowErrors()
         }
      }, textStyle = MaterialTheme.typography.bodyMedium, isError = textState.showErrors(), keyboardOptions = KeyboardOptions.Default.copy(imeAction = imeAction, keyboardType = KeyboardType.Email), keyboardActions = KeyboardActions(onDone = { onImeAction() }), singleLine = true)
   textState.getError()
      ?.let { error -> TextFieldError(textError = error) }
}

@Composable
fun SurName(textState: TextFieldState = remember { TextFieldState() }, imeAction: ImeAction = ImeAction.Next, onImeAction: () -> Unit = {}) {
   OutlinedTextField(value = textState.text, onValueChange = { textState.text = it }, label = {
      Text(text = stringResource(id = R.string.surName), style = MaterialTheme.typography.bodyMedium)
   }, modifier = Modifier
      .fillMaxWidth()
      .onFocusChanged { focusState ->
         textState.onFocusChange(focusState.isFocused)

         if (!focusState.isFocused) {
            textState.enableShowErrors()
         }
      }, textStyle = MaterialTheme.typography.bodyMedium, isError = textState.showErrors(), keyboardOptions = KeyboardOptions.Default.copy(imeAction = imeAction, keyboardType = KeyboardType.Email), keyboardActions = KeyboardActions(onDone = { onImeAction() }), singleLine = true)
   textState.getError()
      ?.let { error -> TextFieldError(textError = error) }
}


///
// Email muestra un campo de texto para el correo electrónico
@Composable
fun CI(ciState: TextFieldState = remember { CIState() }, imeAction: ImeAction = ImeAction.Next, onImeAction: () -> Unit = {}) {
   OutlinedTextField(value = ciState.text, onValueChange = { ciState.text = it }, label = {
      Text(text = stringResource(id = R.string.CI), style = MaterialTheme.typography.bodyMedium)
   }, modifier = Modifier
      .fillMaxWidth()
      .onFocusChanged { focusState ->
         ciState.onFocusChange(focusState.isFocused)

         if (!focusState.isFocused) {
            ciState.enableShowErrors()
         }
      }, textStyle = MaterialTheme.typography.bodyMedium, isError = ciState.showErrors(), keyboardOptions = KeyboardOptions.Default.copy(imeAction = imeAction, keyboardType = KeyboardType.Email), keyboardActions = KeyboardActions(onDone = { onImeAction() }), singleLine = true)
   ciState.getError()
      ?.let { error -> TextFieldError(textError = error) }
}




///
// TextFieldError muestra un mensaje de error debajo del campo de texto
@Composable
fun TextFieldError(textError: String) {
   Row(modifier = Modifier.fillMaxWidth()) {
      Spacer(modifier = Modifier.width(16.dp))
      Text(text = textError, modifier = Modifier.fillMaxWidth(), color = MaterialTheme.colorScheme.error)
   }
}



///
@Preview
@Composable
fun LoginRegisterScreenPreview() {
   ThemeStudyHub {
      Surface {
         LoginRegisterScreen(modifier = Modifier.fillMaxSize(), content = {
            Column {
               CI(ciState = remember { CIState() })
            }
         })
         LoginRegisterTopAppBar(topAppBarText = stringResource(id = R.string.sign_in), onNavUp = {})
      }
   }
}
