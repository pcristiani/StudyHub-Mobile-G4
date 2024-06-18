package com.example.compose.studyhub.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.studyhub.R
import com.example.compose.studyhub.domain.CIState
import com.example.compose.studyhub.domain.DateTimeState
import com.example.compose.studyhub.domain.EmailState
import com.example.compose.studyhub.domain.TextFieldState
import com.example.compose.studyhub.ui.theme.ThemeStudyHub
import com.example.compose.studyhub.ui.theme.stronglyDeemphasizedAlpha

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

@Composable
fun Email(emailState: TextFieldState = remember { EmailState() }, imeAction: ImeAction = ImeAction.Next, onImeAction: () -> Unit = {}) {
   OutlinedTextField(value = emailState.text, onValueChange = { emailState.text = it }, label = {
      Text(text = stringResource(id = R.string.email), style = MaterialTheme.typography.bodyMedium)
   }, modifier = Modifier
      .fillMaxWidth()
      .onFocusChanged { focusState ->
         emailState.onFocusChange(focusState.isFocused)

         if (!focusState.isFocused) {
            emailState.enableShowErrors()
         }
      }, textStyle = MaterialTheme.typography.bodyMedium, isError = emailState.showErrors(), keyboardOptions = KeyboardOptions.Default.copy(imeAction = imeAction, keyboardType = KeyboardType.Email), keyboardActions = KeyboardActions(onDone = { onImeAction() }), singleLine = true)
   emailState.getError()
      ?.let { error -> TextFieldError(textError = error) }
}

@Composable
fun Birthday(birthdayState: DateTimeState = remember { DateTimeState() }, imeAction: ImeAction = ImeAction.Next, onImeAction: () -> Unit = {}) {
   Column(verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()){
      Text(
         text= stringResource(id=R.string.birthday),
         style = MaterialTheme.typography.bodyMedium
      )
      Row(horizontalArrangement = Arrangement.spacedBy(8.dp),
         modifier = Modifier.padding(vertical = 8.dp)
      ){
         //Día
         OutlinedTextField(value = birthdayState.day, onValueChange = {birthdayState.day = it}, label = {Text(text = stringResource(id = R.string.day))}, modifier = Modifier.weight(1f).onFocusChanged { focusState -> birthdayState.onFocusChange(focusState.isFocused)
            if(!focusState.isFocused){
               birthdayState.enableShowErrors()
            }

         },
            textStyle = MaterialTheme.typography.bodyMedium,
            isError = birthdayState.showErrors(),
            keyboardOptions = KeyboardOptions.Default.copy(
               imeAction = ImeAction.Next,
               keyboardType = KeyboardType.Number
            ),
            keyboardActions = KeyboardActions(onNext = { onImeAction() }),
            singleLine = true
         )

         //Mes
         OutlinedTextField(value = birthdayState.month, onValueChange = {birthdayState.month = it}, label = {Text(text = stringResource(id = R.string.month))}, modifier = Modifier.weight(1f).onFocusChanged { focusState -> birthdayState.onFocusChange(focusState.isFocused)
            if(!focusState.isFocused){
               birthdayState.enableShowErrors()
            }

         },
            textStyle = MaterialTheme.typography.bodyMedium,
            isError = birthdayState.showErrors(),
            keyboardOptions = KeyboardOptions.Default.copy(
               imeAction = ImeAction.Next,
               keyboardType = KeyboardType.Number
            ),
            keyboardActions = KeyboardActions(onNext = { onImeAction() }),
            singleLine = true
         )

         //Año
         OutlinedTextField(value = birthdayState.year, onValueChange = {birthdayState.year = it}, label = {Text(text = stringResource(id = R.string.year))}, modifier = Modifier.weight(1f).onFocusChanged { focusState -> birthdayState.onFocusChange(focusState.isFocused)
            if(!focusState.isFocused){
               birthdayState.enableShowErrors()
            }

         },
            textStyle = MaterialTheme.typography.bodyMedium,
            isError = birthdayState.showErrors(),
            keyboardOptions = KeyboardOptions.Default.copy(
               imeAction = ImeAction.Done,
               keyboardType = KeyboardType.Number
            ),
            keyboardActions = KeyboardActions(onNext = { onImeAction() }),
            singleLine = true
         )
      }
      birthdayState.getError()
         ?.let { error -> TextFieldError(textError = error) }
   }
}

///
// Password muestra un campo de texto para la contraseña
@Composable
fun Password(label: String, passwordState: TextFieldState, modifier: Modifier = Modifier, imeAction: ImeAction = ImeAction.Done, onImeAction: () -> Unit = {}) {
   val showPassword = rememberSaveable { mutableStateOf(false) }
   OutlinedTextField(value = passwordState.text, onValueChange = {
      passwordState.text = it
      passwordState.enableShowErrors()
   }, modifier = modifier
      .fillMaxWidth()
      .onFocusChanged { focusState ->
         passwordState.onFocusChange(focusState.isFocused)
         if (!focusState.isFocused) {
            passwordState.enableShowErrors()
         }
      }, textStyle = MaterialTheme.typography.bodyMedium, label = {
      Text(
         text = label,
         style = MaterialTheme.typography.bodyMedium,
          )
   }, trailingIcon = {
      if (showPassword.value) {
         IconButton(onClick = { showPassword.value = false }) {
            Icon(imageVector = Icons.Filled.Visibility, contentDescription = stringResource(id = R.string.hide_password))
         }
      } else {
         IconButton(onClick = { showPassword.value = true }) {
            Icon(imageVector = Icons.Filled.VisibilityOff, contentDescription = stringResource(id = R.string.show_password))
         }
      }
   }, visualTransformation = if (showPassword.value) {
      VisualTransformation.None
   } else {
      PasswordVisualTransformation()
   }, isError = passwordState.showErrors(), supportingText = {
      passwordState.getError()
         ?.let { error -> TextFieldError(textError = error) }
   }, keyboardOptions = KeyboardOptions.Default.copy(imeAction = imeAction, keyboardType = KeyboardType.Password), keyboardActions = KeyboardActions(onDone = { onImeAction() }), singleLine = true)
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
               Password(label = stringResource(id = R.string.password), passwordState = remember { EmailState() })
            }
         })
         LoginRegisterTopAppBar(topAppBarText = stringResource(id = R.string.sign_in), onNavUp = {})
      }
   }
}
