package com.example.compose.studyhub.ui.screen.estudiante

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.studyhub.R
import com.example.compose.studyhub.data.UserRepository
import com.example.compose.studyhub.domain.TextFieldState
import com.example.compose.studyhub.domain.datePicker
import com.example.compose.studyhub.domain.formatDate
import com.example.compose.studyhub.ui.component.ConfirmDialogBox
import com.example.compose.studyhub.ui.component.loginRegisterEdit.Birthday
import com.example.compose.studyhub.ui.component.loginRegisterEdit.Email
import com.example.compose.studyhub.ui.screen.LoginRegisterScreen
import com.example.compose.studyhub.ui.screen.LoginRegisterTopAppBar
import com.example.compose.studyhub.ui.screen.Name
import com.example.compose.studyhub.ui.screen.RecoverPassBox
import com.example.compose.studyhub.ui.screen.SurName
import com.example.compose.studyhub.ui.theme.ThemeStudyHub
import com.example.compose.studyhub.util.supportWideScreen
import kotlinx.coroutines.launch

@Composable
fun EditarPerfilScreen(
   onProfileEditSubmitted: (nombre: String, apellido: String, email: String, fechaNacimiento:String) -> Unit,
    onNavUp: () -> Unit,
): DrawerState {
    Scaffold(topBar = {
        LoginRegisterTopAppBar(
            topAppBarText = stringResource(id = R.string.txt_editPerfil),
            onNavUp = onNavUp
        )
        }, content = {contentPadding -> LoginRegisterScreen(contentPadding = contentPadding, modifier = Modifier.supportWideScreen()){
        Column{
            EditarPerfil(modifier = Modifier.weight(1f), onProfileEditSubmitted = onProfileEditSubmitted)
        }
    }
    })
    return DrawerState(DrawerValue.Closed)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditarPerfil(modifier: Modifier,onProfileEditSubmitted: (nombre: String, apellido: String, email: String, fechaNacimiento:String) -> Unit,){
    val showConfirmationDialog = remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()){
        val focusRequest = remember { FocusRequester() }


        val nombre = UserRepository.getNombre()
        val apellido = UserRepository.getApellido()
        val email = UserRepository.getEmail()


        val nameState = remember { val textFieldState = TextFieldState()
            textFieldState.text = nombre ?: ""
            textFieldState }
        Name(nameState, onImeAction = { focusRequest.requestFocus() })

        Spacer(modifier = Modifier.height(16.dp))
        val surNameState = remember { val textFieldState = TextFieldState()
            textFieldState.text = apellido ?: ""
            textFieldState }
        SurName(surNameState, onImeAction = { focusRequest.requestFocus() })

        Spacer(modifier = Modifier.height(16.dp))
        val emailState = remember{ val textFieldState = TextFieldState()
            textFieldState.text = email ?: ""
            textFieldState }
        Email(emailState, onImeAction = { focusRequest.requestFocus() })

        Spacer(modifier = Modifier.height(16.dp))
        val birthdayState = datePicker()
        Birthday(birthdayState, onImeAction = { focusRequest.requestFocus() })


        Spacer(modifier = Modifier.height(15.dp))


        Button(onClick = { showConfirmationDialog.value = true
         }, modifier = Modifier.fillMaxWidth()) { Text(text = stringResource(id = R.string.txt_editPerfil)) }

        if(showConfirmationDialog.value){
            ConfirmDialogBox(onDismissRequest = { onProfileEditSubmitted(nameState.text, surNameState.text, emailState.text,
                formatDate(birthdayState)); showConfirmationDialog.value = false }, dialogTitle = "Perfil modificado con éxito")
        }
    }


}

@Preview
@Composable
fun EditarPerfilScreenPreview(){
    ThemeStudyHub {
        EditarPerfilScreen(onProfileEditSubmitted = { _, _, _, _ -> },
            onNavUp = {},
            )
    }
}