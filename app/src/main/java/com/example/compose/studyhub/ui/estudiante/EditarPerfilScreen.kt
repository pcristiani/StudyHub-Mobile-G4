package com.example.compose.studyhub.ui.estudiante

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
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
import com.example.compose.studyhub.domain.DateTimeState
import com.example.compose.studyhub.domain.EmailState
import com.example.compose.studyhub.domain.PasswordState
import com.example.compose.studyhub.domain.TextFieldState
import com.example.compose.studyhub.ui.screen.Birthday
import com.example.compose.studyhub.ui.screen.CI
import com.example.compose.studyhub.ui.screen.Email
import com.example.compose.studyhub.ui.screen.LoginRegisterScreen
import com.example.compose.studyhub.ui.screen.LoginRegisterTopAppBar
import com.example.compose.studyhub.ui.screen.Name
import com.example.compose.studyhub.ui.screen.OrLoginInvitados
import com.example.compose.studyhub.ui.screen.Password
import com.example.compose.studyhub.ui.screen.SurName
import com.example.compose.studyhub.ui.theme.ThemeStudyHub
import com.example.compose.studyhub.ui.theme.stronglyDeemphasizedAlpha
import com.example.compose.studyhub.util.supportWideScreen

@Composable
fun EditarPerfilScreen(
    onProfileEditSubmitted: (nombre: String?, apellido: String?, email: String?, fechaNacimiento:String?, password: String?) -> Unit,
    onNavUp: () -> Unit,
): DrawerState {
    Scaffold(topBar = {
        LoginRegisterTopAppBar(
            topAppBarText = stringResource(id = R.string.txt_editPerfil),
            onNavUp = onNavUp
        )

        }, content = {contentPadding -> LoginRegisterScreen(onLoginInvitado = {}, contentPadding = contentPadding, modifier = Modifier.supportWideScreen()){
        Column{
            EditarPerfil(modifier = Modifier.weight(1f), onProfileEditSubmitted = onProfileEditSubmitted)
        }
    }
    })
    return DrawerState(DrawerValue.Closed)
}

@Composable
fun EditarPerfil(modifier: Modifier,
                 onProfileEditSubmitted: (nombre: String, apellido: String, email: String, fechaNacimiento:String, password: String) -> Unit,
                 ){
    Column(modifier = Modifier.fillMaxWidth()){
        val passwordFocusRequest = remember { FocusRequester() }
        val confirmationPasswordFocusRequest = remember { FocusRequester() }


        val nameState = remember { TextFieldState() }
        Name(nameState, onImeAction = { passwordFocusRequest.requestFocus() })

        Spacer(modifier = Modifier.height(16.dp))
        val surNameState = remember { TextFieldState() }
        SurName(surNameState, onImeAction = { passwordFocusRequest.requestFocus() })

        Spacer(modifier = Modifier.height(16.dp))
        val emailState = remember{ EmailState() }
        Email(emailState, onImeAction = { passwordFocusRequest.requestFocus() })

        Spacer(modifier = Modifier.height(16.dp))
        val birthdayState = remember{ DateTimeState() }
        Birthday(birthdayState, onImeAction = { passwordFocusRequest.requestFocus() })

        Spacer(modifier = Modifier.height(16.dp))
        val passwordState = remember { PasswordState() }
        Password(label = stringResource(id = R.string.password), passwordState = passwordState, imeAction = ImeAction.Next, onImeAction = { confirmationPasswordFocusRequest.requestFocus() }, modifier = Modifier.focusRequester(passwordFocusRequest))

        Spacer(modifier = Modifier.height(1.dp))
        val confirmPasswordState = remember { ConfirmPasswordState(passwordState = passwordState) }
        Password(label = stringResource(id = R.string.confirm_password), passwordState = confirmPasswordState, onImeAction = { onProfileEditSubmitted(nameState.text, surNameState.text, emailState.text,"05/12/2000", passwordState.text) }, modifier = Modifier.focusRequester(confirmationPasswordFocusRequest))

        Spacer(modifier = Modifier.height(15.dp))

        Button(onClick = { onProfileEditSubmitted(nameState.text, surNameState.text, emailState.text,birthdayState.day + {"/"} + birthdayState.month + {"/"} + birthdayState.year, passwordState.text) }, modifier = Modifier.fillMaxWidth(), passwordState.isValid && confirmPasswordState.isValid) { Text(text = stringResource(id = R.string.txt_editPerfil)) }
    }
}

@Preview
@Composable
fun EditarPerfilScreenPreview(){
    ThemeStudyHub {
        EditarPerfilScreen(onProfileEditSubmitted = { _, _, _, _, _ -> },
            onNavUp = {},
            )
    }
}