package com.example.compose.studyhub.ui.screen

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.studyhub.R
import com.example.compose.studyhub.domain.CIState
import com.example.compose.studyhub.domain.CIStateSaver
import com.example.compose.studyhub.domain.EmailState
import com.example.compose.studyhub.domain.EmailStateSaver
import com.example.compose.studyhub.domain.PasswordState
import com.example.compose.studyhub.ui.component.adviceSnackbar
import com.example.compose.studyhub.ui.component.errorSnackbar
import com.example.compose.studyhub.ui.theme.ThemeStudyHub
import com.example.compose.studyhub.util.supportWideScreen
import kotlinx.coroutines.launch
import com.example.compose.studyhub.ui.component.loginRegisterEdit.Password
import com.example.compose.studyhub.ui.route.RecoverPassRoute

///
@Composable
fun LoginScreen(
   ci: String?,
   onLoginSubmitted: (ci: String, password: String) -> Unit,
   onNavigateToRegister: (ci: String) -> Unit,
   onNavUp: () -> Unit,
   loginError: String?,
   onErrorDismissed: () -> Unit
               ) {
   val snackbarHostState = remember { SnackbarHostState() }

   Scaffold(topBar = {
      LoginRegisterTopAppBar(topAppBarText = stringResource(id = R.string.sign_in), onNavUp = onNavUp)
   },
      snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
      content = { contentPadding ->
      LoginRegisterScreen(modifier = Modifier.supportWideScreen(), contentPadding = contentPadding) {
         Column(modifier = Modifier.fillMaxWidth()) {
            LoggingIn(ci = ci, onLoginSubmitted = onLoginSubmitted, onRegisterSubmitted = onNavigateToRegister, snackbarHostState = snackbarHostState, loginError = loginError)
            Spacer(modifier = Modifier.height(5.dp))

         }


      }
   })
}


@Composable
fun LoggingIn(
   ci: String?,
   onLoginSubmitted: (ci: String, password: String) -> Unit,
   onRegisterSubmitted: (ci: String) -> Unit,
   snackbarHostState: SnackbarHostState,
   loginError: String?
             ) {
   val scope = rememberCoroutineScope()
   val showRecoverPassDialog = remember {mutableStateOf(false)}
   val showSnackbar = remember {mutableStateOf(false)}
   var snackbarMessage = remember {mutableStateOf("")}


   Column(modifier = Modifier.fillMaxWidth()) {
      val focusRequester = remember { FocusRequester() }
      val ciState by rememberSaveable(stateSaver = CIStateSaver) { mutableStateOf(CIState(ci)) }
      
      CI(ciState = ciState, onImeAction = { focusRequester.requestFocus() })
      Spacer(modifier = Modifier.height(16.dp))
      val passwordState = remember { PasswordState() }
      val onSubmit = {
         if (ciState.isValid && passwordState.isValid) {
            onLoginSubmitted(ciState.text, passwordState.text)
         }
      }

      val onSubmitRegister = {
         if (ciState.isValid) {
            onRegisterSubmitted(ciState.text)
         }
      }
      
      Password(label = stringResource(id = R.string.password), passwordState = passwordState, modifier = Modifier.focusRequester(focusRequester), onImeAction = { onSubmit() })
      Spacer(modifier = Modifier.height(16.dp))

      Button(onClick = { onSubmit();
         if(loginError!=null){
            errorSnackbar(loginError, snackbarHostState, scope)
         }

         showSnackbar.value = true

      }, modifier = Modifier
         .fillMaxWidth()
         .padding(top = 16.dp, bottom = 10.dp), enabled = ciState.isValid && passwordState.isValid) {
         Text(text = stringResource(id = R.string.sign_in))
      }

      TextButton(onClick = {
         showRecoverPassDialog.value = true
      }, modifier = Modifier.fillMaxWidth()) { Text(text = stringResource(id = R.string.forgot_password)) }

      Button(onClick = { onSubmitRegister() }, modifier = Modifier
         .fillMaxWidth()
         .padding(vertical = 16.dp), enabled = ciState.isValid, colors = ButtonDefaults.buttonColors(
         containerColor = Color.Transparent,
         contentColor = Color.Blue // Text color when the button is enabled
      ),border = BorderStroke(1.dp, Color.Blue)
      ) {
         Text(text = stringResource(id = R.string.create_account))
      }

   }

   if(showRecoverPassDialog.value){
      RecoverPassBox(onConfirmation = {adviceSnackbar(it, snackbarHostState, scope)}, onDismiss = {showRecoverPassDialog.value = false})
   }


}


@Composable
fun RecoverPassBox(onConfirmation: (String) -> Unit, onDismiss: () -> Unit){
   RecoverPassRoute(onConfirmation = {

     onConfirmation(it) },
      dialogTitle = "Recuperar contraseña",
      onDismiss = {onDismiss()},
      onFail = {onConfirmation("Error: $it")}
   )

}


/*@Composable
fun ErrorSnackbar(message: String, snackbarHostState: SnackbarHostState, modifier: Modifier = Modifier, onDismiss: () -> Unit = {}) {
   SnackbarHost(hostState = snackbarHostState, snackbar = { data ->
      println("Hello")
      Snackbar(modifier = Modifier.padding(16.dp), content = {
         Text(text = message, style = MaterialTheme.typography.bodyMedium)
      }, action = {
         data.visuals.actionLabel?.let {
            TextButton(onClick = onDismiss) {
               Text(text = stringResource(id = R.string.txt_close), color = MaterialTheme.colorScheme.inversePrimary)
            }
         }
      })
   }, modifier = modifier
      .fillMaxWidth()
      .wrapContentHeight(Alignment.Bottom))
}*/





@Preview(name = "Sign in dark theme", uiMode = UI_MODE_NIGHT_NO)
/*@Preview(name = "Sign in dark theme", uiMode = UI_MODE_NIGHT_YES)*/
@Composable
fun LoginPreview() {
   ThemeStudyHub {
      LoginScreen(ci = null, onLoginSubmitted = { _, _ -> }, onNavigateToRegister = { _, ->}, onNavUp = {}, "ERROR", onErrorDismissed = {})
   }
}

