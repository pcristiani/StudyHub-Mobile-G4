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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
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
import com.example.compose.studyhub.R
import com.example.compose.studyhub.domain.EmailState
import com.example.compose.studyhub.domain.EmailStateSaver
import com.example.compose.studyhub.domain.PasswordState
import com.example.compose.studyhub.ui.theme.ThemeStudyHub
import com.example.compose.studyhub.ui.theme.stronglyDeemphasizedAlpha
import com.example.compose.studyhub.util.supportWideScreen
import kotlinx.coroutines.launch

///
@Composable
fun LoginScreen(
   ci: String?,
   onLoginSubmitted: (ci: String, password: String) -> Unit,
   onLoginInvitado: () -> Unit,
   onNavUp: () -> Unit,
               ) {
   val snackbarHostState = remember { SnackbarHostState() }
   val scope = rememberCoroutineScope()
   val respuesta = stringResource(id = R.string.login_test)
   val snackbarActionLabel = stringResource(id = R.string.sign_in)
   
   Scaffold(topBar = {
      LoginRegisterTopAppBar(topAppBarText = stringResource(id = R.string.sign_in), onNavUp = onNavUp)
   }, content = { contentPadding ->
      LoginRegisterScreen(modifier = Modifier.supportWideScreen(), contentPadding = contentPadding, onLoginInvitado = onLoginInvitado) {
         Column(modifier = Modifier.fillMaxWidth()) {
            LoginTest(ci = ci, onLoginSubmitted = onLoginSubmitted, onLoginInvitado = onLoginInvitado)
            Spacer(modifier = Modifier.height(5.dp))
            
            TextButton(onClick = {
               scope.launch {
                  snackbarHostState.showSnackbar(message = respuesta, actionLabel = snackbarActionLabel)
               }
            }, modifier = Modifier.fillMaxWidth()) { Text(text = stringResource(id = R.string.forgot_password)) }
         }
      }
   })
   
   Box(modifier = Modifier.fillMaxSize()) {
      ErrorSnackbar(snackbarHostState = snackbarHostState, onDismiss = { snackbarHostState.currentSnackbarData?.dismiss() }, modifier = Modifier.align(Alignment.BottomCenter))
   }
}


///
@Composable
fun LoginTest(
   ci: String?,
   onLoginSubmitted: (email: String, password: String) -> Unit,
   onLoginInvitado: () -> Unit,
             ) {
   val snackbarHostState = remember { SnackbarHostState() }
   val scope = rememberCoroutineScope()
   
   Column(modifier = Modifier.fillMaxWidth()) {
      val focusRequester = remember { FocusRequester() }
      val ciState by rememberSaveable(stateSaver = EmailStateSaver) { mutableStateOf(EmailState(ci)) }
      
      Email(ciState = ciState, onImeAction = { focusRequester.requestFocus() })
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
      
      Password(label = stringResource(id = R.string.password), passwordState = passwordState, modifier = Modifier.focusRequester(focusRequester), onImeAction = { onSubmit() })
      Spacer(modifier = Modifier.height(16.dp))
      
      Button(onClick = { onSubmit() }, modifier = Modifier
         .fillMaxWidth()
         .padding(vertical = 16.dp), enabled = ciState.isValid && passwordState.isValid) {
         Text(text = stringResource(id = R.string.sign_in))
      }
   }
   
   Box(modifier = Modifier.fillMaxSize()) {
      ErrorSnackbar(snackbarHostState = snackbarHostState, onDismiss = { snackbarHostState.currentSnackbarData?.dismiss() }, modifier = Modifier.align(Alignment.BottomCenter))
   }
}


///
private fun getLoginTest(emailLogin: String, passwordLogin: String): Boolean {
   println("SEBASTIAN: $emailLogin")
   return true
}


@Composable
fun ErrorSnackbar(snackbarHostState: SnackbarHostState, modifier: Modifier = Modifier, onDismiss: () -> Unit = {}) {
   SnackbarHost(hostState = snackbarHostState, snackbar = { data ->
      Snackbar(modifier = Modifier.padding(16.dp), content = {
         Text(text = data.visuals.message, style = MaterialTheme.typography.bodyMedium)
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


///
@Composable
fun OrLoginInvitados(onLoginInvitado: () -> Unit, modifier: Modifier = Modifier) {
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
@Preview(name = "Sign in dark theme", uiMode = UI_MODE_NIGHT_NO)
/*@Preview(name = "Sign in dark theme", uiMode = UI_MODE_NIGHT_YES)*/
@Composable
fun LoginPreview() {
   ThemeStudyHub {
      LoginScreen(ci = null, onLoginSubmitted = { _, _ -> }, onLoginInvitado = {}, onNavUp = {})
   }
}
