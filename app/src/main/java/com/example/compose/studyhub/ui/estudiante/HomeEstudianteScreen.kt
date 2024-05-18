package com.example.compose.studyhub.ui.estudiante

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.compose.studyhub.R
import com.example.compose.studyhub.data.UserRepository
import com.example.compose.studyhub.domain.EmailState
import com.example.compose.studyhub.domain.EmailStateSaver
import com.example.compose.studyhub.ui.component.registrarEstudiante.QuestionScreenData
import com.example.compose.studyhub.ui.navigation.MenuLateral
import com.example.compose.studyhub.ui.navigation.TopBar
import com.example.compose.studyhub.ui.screen.Email
import com.example.compose.studyhub.ui.screen.LoginRegisterTopAppBar
import com.example.compose.studyhub.ui.screen.OrLoginInvitados
import com.example.compose.studyhub.ui.theme.ThemeStudyHub
import com.example.compose.studyhub.util.supportWideScreen

@Composable
fun HomeEstudianteScreen(
   surveyScreenData: QuestionScreenData,
   isNextEnabled: Boolean,
   onClosePressed: () -> Unit,
   onPreviousPressed: () -> Unit,
   onNextPressed: () -> Unit,
   onDonePressed: () -> Unit,
   content: @Composable (PaddingValues) -> Unit,
) {
} ///

@Composable
fun QuestionResultScreen2(onDonePressed: () -> Unit) {
   val navController = rememberNavController()
   val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
   val scope = rememberCoroutineScope()
   val respuesta = stringResource(id = R.string.login_test)
   val snackbarHostState = remember { SnackbarHostState() }
   val snackbarActionLabel = stringResource(id = R.string.sign_in)
   MenuLateral(navController = navController, drawerState = drawerState) {
      Contenido2(navController, drawerState, onDonePressed)
   }/*  }) Button(onClick = onSubmit, modifier = Modifier
      .fillMaxWidth()
      .padding(top = 24.dp, bottom = 8.dp)) {
      Text(text = stringResource(id = R.string.next), style = MaterialTheme.typography.titleSmall)
   }
   QuestionResult(title = stringResource(R.string.result_login), subtitle = "strEmail", modifier = "modifier")
    MenuLateral(navController = navController, drawerState = drawerState) {

      Contenido(navController = navController, drawerState = drawerState, onDonePressed = onDonePressed)
   }*/
} // email: String?,

///
@Composable
fun Contenido(
   navController: NavHostController,
   drawerState: DrawerState,
   onDonePressed: () -> Unit,
) {
   val u = UserRepository.user.toString()
   val partes = u.split("=")
   val strEmail = partes[1].dropLast(1)
   println("----> $strEmail")
   Surface(modifier = Modifier.supportWideScreen()) {
      Scaffold(topBar = {
         TopBar(drawerState)
      }, content = { innerPadding ->
         Column(modifier = Modifier
            .padding(innerPadding)
            .fillMaxWidth()) {
            Image(painter = painterResource(id = R.drawable.a21_alien_128), modifier = Modifier.size(260.dp), contentDescription = "Logo") // LoginCreateAccount(onLoginRegister = { }, onLoginInvitado = { }, onFocusChange = { })
         }/*  LoginCreateAccount(onLoginRegister = { }, onLoginInvitado = { }, onFocusChange = { })*/
      })
   }
} //  onLoginSubmitted: (email: String, password: String) -> Unit,

///
// era privada
@Composable
fun LoginCreateAccount(onLoginRegister: (email: String) -> Unit, onLoginInvitado: () -> Unit, onFocusChange: (Boolean) -> Unit, modifier: Modifier = Modifier) { // ! Guardar y resturar un estado
   val emailState by rememberSaveable(stateSaver = EmailStateSaver) { mutableStateOf(EmailState()) }

   Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
      val onSubmit = {
         if (emailState.isValid) {
            onLoginRegister(emailState.text)
         } else {
            emailState.enableShowErrors()
         }
      }
      onFocusChange(emailState.isFocused)
      val u = UserRepository.user.toString()
      val partes = u.split("=")
      val strEmail = partes[1].dropLast(1)
      Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
         Image(painter = painterResource(id = R.drawable.logotext), contentDescription = null)
      }
      Email(emailState = emailState, imeAction = ImeAction.Done, onImeAction = onSubmit)
      Button(onClick = onSubmit, modifier = Modifier
         .fillMaxWidth()
         .padding(top = 24.dp, bottom = 8.dp)) {
         Text(text = stringResource(id = R.string.txt_alien), style = MaterialTheme.typography.titleSmall)
      }
      OrLoginInvitados(onLoginInvitado = onLoginInvitado, modifier = Modifier.fillMaxWidth())
   }
}

@Composable
fun Contenido2(
   navController: NavHostController,
   drawerState: DrawerState,
   onDonePressed: () -> Unit,
) {
   val u = UserRepository.user.toString()
   val partes = u.split("=")
   val strEmail = partes[1].dropLast(1)
   println("----> $strEmail")

   Surface(modifier = Modifier.supportWideScreen()) {
      Scaffold(topBar = {
         TopBar(drawerState)
      }, content = { innerPadding ->
         Column(modifier = Modifier
            .padding(innerPadding)
            .fillMaxWidth()) { // LoginCreateAccount(onLoginRegister = { }, onLoginInvitado = { }, onFocusChange = { })
         }
         LoginCreateAccount(onLoginRegister = { }, onLoginInvitado = { }, onFocusChange = { })
      })
   }
}

@Preview
@Composable
fun LoginPreview() {
   ThemeStudyHub {
      LoginRegisterTopAppBar(topAppBarText = stringResource(id = R.string.sign_in), onNavUp = { })
      LoginCreateAccount(onLoginRegister = { }, onLoginInvitado = { }, onFocusChange = { })
   }
}

