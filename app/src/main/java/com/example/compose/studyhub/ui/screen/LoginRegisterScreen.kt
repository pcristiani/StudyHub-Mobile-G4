package com.example.compose.studyhub.ui.screen

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
import com.example.compose.studyhub.domain.EmailState
import com.example.compose.studyhub.domain.TextFieldState
import com.example.compose.studyhub.ui.theme.ThemeStudyHub
import com.example.compose.studyhub.ui.theme.stronglyDeemphasizedAlpha

///
// LoginRegisterScreen muestra la pantalla de inicio de sesión y registro
@Composable
fun LoginRegisterScreen(onLoginInvitado: () -> Unit, modifier: Modifier = Modifier, contentPadding: PaddingValues = PaddingValues(), content: @Composable () -> Unit) {
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
      Text(text = stringResource(id = R.string.CI), style = MaterialTheme.typography.bodyMedium)
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
// OrLoginInvitado muestra un botón para iniciar sesión como invitado
@Composable
fun OrLoginInvitado(onLoginInvitado: () -> Unit, modifier: Modifier = Modifier) {
   Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
      Text(text = stringResource(id = R.string.or), style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.onSurface.copy(alpha = stronglyDeemphasizedAlpha), modifier = Modifier.paddingFromBaseline(top = 20.dp))
      OutlinedButton(
         onClick = onLoginInvitado,
         modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, bottom = 20.dp),
                    ) { Text(text = stringResource(id = R.string.txt_btn_invitado)) }
   }
}


///
@Preview
@Composable
fun LoginRegisterScreenPreview() {
   ThemeStudyHub {
      Surface {
         LoginRegisterScreen(onLoginInvitado = {}, modifier = Modifier.fillMaxSize(), content = {
            Column {
               CI(ciState = remember { CIState() })
               Password(label = stringResource(id = R.string.password), passwordState = remember { EmailState() })
            }
         })
         LoginRegisterTopAppBar(topAppBarText = stringResource(id = R.string.sign_in), onNavUp = {})
      }
   }
}
