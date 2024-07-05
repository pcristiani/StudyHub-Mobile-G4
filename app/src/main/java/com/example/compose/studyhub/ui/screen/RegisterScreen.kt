package com.example.compose.studyhub.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.studyhub.R
import com.example.compose.studyhub.domain.CIState
import com.example.compose.studyhub.domain.ConfirmPasswordState
import com.example.compose.studyhub.domain.EmailState
import com.example.compose.studyhub.domain.PasswordState
import com.example.compose.studyhub.domain.TextFieldState
import com.example.compose.studyhub.domain.datePicker
import com.example.compose.studyhub.domain.formatDate
import com.example.compose.studyhub.ui.component.loginRegisterEdit.Birthday
import com.example.compose.studyhub.ui.component.loginRegisterEdit.Email
import com.example.compose.studyhub.ui.component.loginRegisterEdit.Password
import com.example.compose.studyhub.ui.theme.ThemeStudyHub
import com.example.compose.studyhub.ui.theme.stronglyDeemphasizedAlpha
import com.example.compose.studyhub.util.supportWideScreen

@Composable
fun RegisterScreen(
   ci: String?,
   onRegisterSubmitted: (nombre: String, apellido: String, email: String, fechaNacimiento:String, ci: String, password: String) -> Unit,
   onNavUp: () -> Unit,
) {
   Scaffold(topBar = {
      LoginRegisterTopAppBar(
         topAppBarText = stringResource(id = R.string.create_account),
         onNavUp = onNavUp,
      )
   }, content = { contentPadding ->
      LoginRegisterScreen(contentPadding = contentPadding, modifier = Modifier.supportWideScreen()) {
         Column {
            RegisterContent(
               ci = ci,
               onRegisterSubmitted = onRegisterSubmitted,
            )
         }
      }
   })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterContent(
   ci: String?,
   onRegisterSubmitted: (nombre: String, apellido: String, email: String, fechaNacimiento:String, ci: String, password: String) -> Unit,
) {
   Column(modifier = Modifier.fillMaxWidth()) {
      val passwordFocusRequest = remember { FocusRequester() }
      val confirmationPasswordFocusRequest = remember { FocusRequester() }

      val nameState = remember { TextFieldState() }
      Name(nameState, onImeAction = { passwordFocusRequest.requestFocus() })

      Spacer(modifier = Modifier.height(16.dp))
      val surNameState = remember { TextFieldState() }
      SurName(surNameState, onImeAction = { passwordFocusRequest.requestFocus() })

      Spacer(modifier = Modifier.height(16.dp))
      val ciState = remember { CIState(ci) }
      CI(ciState, onImeAction = { passwordFocusRequest.requestFocus() })

      Spacer(modifier = Modifier.height(16.dp))
      val emailState = remember{EmailState()}
      Email(emailState, onImeAction = { passwordFocusRequest.requestFocus() })

      Spacer(modifier = Modifier.height(16.dp))
      val birthdayState = datePicker()
      Birthday(birthdayState,onImeAction = { passwordFocusRequest.requestFocus()} )

      Spacer(modifier = Modifier.height(16.dp))
      val passwordState = remember { PasswordState() }
      Password(label = stringResource(id = R.string.password), passwordState = passwordState, imeAction = ImeAction.Next, onImeAction = { confirmationPasswordFocusRequest.requestFocus() }, modifier = Modifier.focusRequester(passwordFocusRequest))

      Spacer(modifier = Modifier.height(1.dp))
      val confirmPasswordState = remember { ConfirmPasswordState(passwordState = passwordState) }
      Password(label = stringResource(id = R.string.confirm_password), passwordState = confirmPasswordState, onImeAction = { onRegisterSubmitted(nameState.text, surNameState.text, emailState.text, formatDate(birthdayState), ciState.text, passwordState.text) }, modifier = Modifier.focusRequester(confirmationPasswordFocusRequest))

      Spacer(modifier = Modifier.height(15.dp))
      Text(text = stringResource(id = R.string.terms_and_conditions), style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurface.copy(alpha = stronglyDeemphasizedAlpha))

      Spacer(modifier = Modifier.height(15.dp))
      Button(onClick = { onRegisterSubmitted(nameState.text, surNameState.text, emailState.text, formatDate(birthdayState), ciState.text, passwordState.text) }, modifier = Modifier.fillMaxWidth(), enabled = ciState.isValid && passwordState.isValid && confirmPasswordState.isValid) { Text(text = stringResource(id = R.string.create_account)) }
   }
}

/*@Preview(widthDp = 1024)*/
@Preview
@Composable
fun RegisterPreview() {
   ThemeStudyHub {
      RegisterScreen(
         ci = null,
         onRegisterSubmitted = { _, _, _, _, _, _ -> },
         onNavUp = {},
      )
   }
}
