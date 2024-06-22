package com.example.compose.studyhub.ui.screen

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.compose.studyhub.R
import com.example.compose.studyhub.domain.EmailState
import com.example.compose.studyhub.domain.EmailStateSaver
import com.example.compose.studyhub.domain.PasswordState
import com.example.compose.studyhub.ui.theme.ThemeStudyHub
import com.example.compose.studyhub.ui.theme.stronglyDeemphasizedAlpha
import com.example.compose.studyhub.util.supportWideScreen
import kotlinx.coroutines.launch
import com.example.compose.studyhub.util.services.PushNotificationService
import com.example.compose.studyhub.ui.component.AlertDialogBoxWithText
import com.example.compose.studyhub.ui.component.loginRegisterEdit.Password
import com.example.compose.studyhub.ui.route.RecoverPassRoute
import kotlinx.coroutines.GlobalScope

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
   val scope = rememberCoroutineScope()
   val respuesta = stringResource(id = R.string.login_test)
   val snackbarActionLabel = stringResource(id = R.string.sign_in)

/*
   if(loginError != null){
      ErrorSnackbar(message="Hola", snackbarHostState = snackbarHostState, onDismiss = { snackbarHostState.currentSnackbarData?.dismiss() }, modifier = Modifier)

   }
*/
   Scaffold(topBar = {
      LoginRegisterTopAppBar(topAppBarText = stringResource(id = R.string.sign_in), onNavUp = onNavUp)
   },
      snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
      content = { contentPadding ->
      LoginRegisterScreen(modifier = Modifier.supportWideScreen(), contentPadding = contentPadding) {
         Column(modifier = Modifier.fillMaxWidth()) {
            LoginTest(ci = ci, onLoginSubmitted = onLoginSubmitted, onRegisterSubmitted = onNavigateToRegister)
            Spacer(modifier = Modifier.height(5.dp))

         }


      }
   })
/*
   Box(modifier = Modifier.fillMaxSize()) {
      ErrorSnackbar(message = "Hola", snackbarHostState = snackbarHostState, onDismiss = { snackbarHostState.currentSnackbarData?.dismiss() }, modifier = Modifier.align(Alignment.BottomCenter))
   }

 */
}


///
@Composable
fun LoginTest(
   ci: String?,
   onLoginSubmitted: (ci: String, password: String) -> Unit,
   onRegisterSubmitted: (ci: String) -> Unit,
             ) {
   val snackbarHostState = remember { SnackbarHostState() }
   val scope = rememberCoroutineScope()
   val respuesta = stringResource(id = R.string.login_test)
   val snackbarActionLabel = stringResource(id = R.string.sign_in)
   val showRecoverPassDialog = remember {mutableStateOf(false)}
   val showSnackbar = remember {mutableStateOf(true)}
   var snackbarMessage = remember {mutableStateOf("")}


   Column(modifier = Modifier.fillMaxWidth()) {
      val focusRequester = remember { FocusRequester() }
      val ciState by rememberSaveable(stateSaver = EmailStateSaver) { mutableStateOf(EmailState(ci)) }
      
      CI(ciState = ciState, onImeAction = { focusRequester.requestFocus() })
      Spacer(modifier = Modifier.height(16.dp))
      val passwordState = remember { PasswordState() }
      val onSubmit = {
         if (ciState.isValid && passwordState.isValid) {
            val validarResponde = getLoginTest(ciState.text, passwordState.text)
            if (validarResponde) {
               print("VALIDADO - " + ciState.text)
               onLoginSubmitted(ciState.text, passwordState.text)

            } else {
               print("NO VALIDADO - " + ciState.text) // onNavigateToRegister()
            }
         }
      }

      val onSubmitRegister = {
         if (ciState.isValid) {
            onRegisterSubmitted(ciState.text)
         }
      }
      
      Password(label = stringResource(id = R.string.password), passwordState = passwordState, modifier = Modifier.focusRequester(focusRequester), onImeAction = { onSubmit() })
      Spacer(modifier = Modifier.height(16.dp))
      
      Button(onClick = { onSubmit() }, modifier = Modifier
         .fillMaxWidth()
         .padding(top = 16.dp, bottom = 10.dp), enabled = ciState.isValid && passwordState.isValid) {
         Text(text = stringResource(id = R.string.sign_in))
      }

      TextButton(onClick = {
         showRecoverPassDialog.value = true
         scope.launch {

            snackbarHostState.showSnackbar(message = respuesta, actionLabel = snackbarActionLabel)

         }

      }, modifier = Modifier.fillMaxWidth()) { Text(text = stringResource(id = R.string.forgot_password)) }

      Button(onClick = { onSubmitRegister() }, modifier = Modifier
         .fillMaxWidth()
         .padding(vertical = 16.dp), enabled = ciState.isValid) {
         Text(text = stringResource(id = R.string.create_account))
      }
   }

   if(showRecoverPassDialog.value == true){
      RecoverPassBox(onConfirmation = {snackbarMessage.value = it; showSnackbar.value = true}, onDismiss = {showRecoverPassDialog.value = false})
   }

   if(showSnackbar.value){
      LaunchedEffect(showSnackbar.value) {
         scope.launch {

            snackbarHostState.showSnackbar(
               message = "snackbarMessage.value",
               actionLabel = "Cerrar",
               duration = SnackbarDuration.Short
            )

         }
         showSnackbar.value = false
      }
   }

}


///
private fun getLoginTest(emailLogin: String, passwordLogin: String): Boolean {
   println("SEBASTIAN: $emailLogin")
   return true
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


@Composable
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
}





@Preview(name = "Sign in dark theme", uiMode = UI_MODE_NIGHT_NO)
/*@Preview(name = "Sign in dark theme", uiMode = UI_MODE_NIGHT_YES)*/
@Composable
fun LoginPreview() {
   ThemeStudyHub {
      LoginScreen(ci = null, onLoginSubmitted = { _, _ -> }, onNavigateToRegister = {_, ->}, onNavUp = {}, "ERROR", onErrorDismissed = {})
   }
}

