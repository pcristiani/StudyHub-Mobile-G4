package com.example.compose.studyhub.ui.screen

import android.app.Activity
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.studyhub.R
import com.example.compose.studyhub.domain.CIState
import com.example.compose.studyhub.domain.CIStateSaver
import com.example.compose.studyhub.ui.component.Logo
import com.example.compose.studyhub.ui.theme.ThemeStudyHub
import com.example.compose.studyhub.ui.theme.stronglyDeemphasizedAlpha
import com.example.compose.studyhub.util.supportWideScreen

// Pantalla de Bienvenida
@Composable
fun InicioScreen(onLoginRegister: (email: String) -> Unit) {
   var showLogoTitle by rememberSaveable { mutableStateOf(true) }
   var context = LocalContext.current
   val activity = context as? Activity

   Scaffold(modifier = Modifier.supportWideScreen()) { innerPadding ->
      Column(modifier = Modifier
         .padding(innerPadding)
         .fillMaxWidth()
         .verticalScroll(rememberScrollState())) {
         AnimatedVisibility(showLogoTitle, Modifier.fillMaxWidth()) { LogoTitle() }
         LoginCreateAccount(onLoginRegister = onLoginRegister, onFocusChange = { focused -> showLogoTitle = !focused }, modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp))
      }
   }

   BackHandler{
      activity?.moveTaskToBack(true);
   }
}

// Logo y Titulo
@Composable
private fun LogoTitle(modifier: Modifier = Modifier) {
   Column(modifier = modifier.wrapContentHeight(align = Alignment.CenterVertically)) {
      Logo(modifier = Modifier
         .align(Alignment.CenterHorizontally)
         .size(240.dp)
         .padding(top = 76.dp))

      Text(text = stringResource(id = R.string.app_title), style = MaterialTheme.typography.displayMedium, textAlign = TextAlign.Center, modifier = Modifier
         .padding(top = 30.dp)
         .fillMaxWidth())
   }
}

// Crear cuenta
@Composable
private fun LoginCreateAccount(onLoginRegister: (email: String) -> Unit, onFocusChange: (Boolean) -> Unit, modifier: Modifier = Modifier) { // ! Guardar y resturar un estado
   val ciState by rememberSaveable(stateSaver = CIStateSaver) { mutableStateOf(CIState()) }

   Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
      Text(text = stringResource(id = R.string.sign_in_create_account), style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface.copy(alpha = stronglyDeemphasizedAlpha), textAlign = TextAlign.Center, modifier = Modifier.padding(top = 100.dp, bottom = 6.dp))
      val onSubmit = { // * Verifica si el email es valido
         if (ciState.isValid) {
            onLoginRegister(ciState.text)
         } else {
            ciState.enableShowErrors()
         }
      }
      onFocusChange(ciState.isFocused)

      CI(ciState = ciState, imeAction = ImeAction.Done, onImeAction = onSubmit)
      Button(onClick = {
         if(ciState.text.isNotEmpty()){
            onSubmit()
         }
      }
      , modifier = Modifier
         .fillMaxWidth()
         .padding(top = 24.dp, bottom = 8.dp)) {
         Text(text = stringResource(id = R.string.next), style = MaterialTheme.typography.titleSmall)
      }
   }
}

@Preview(name = "Inicio dark theme", uiMode = UI_MODE_NIGHT_NO)
@Composable
fun InicioScreenPreview() {
   ThemeStudyHub {
      InicioScreen(
         onLoginRegister = {},
      )
   }
}
