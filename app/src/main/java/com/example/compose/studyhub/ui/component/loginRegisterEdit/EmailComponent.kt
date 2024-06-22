package com.example.compose.studyhub.ui.component.loginRegisterEdit

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.example.compose.studyhub.R
import com.example.compose.studyhub.domain.EmailState
import com.example.compose.studyhub.domain.TextFieldState
import com.example.compose.studyhub.ui.screen.TextFieldError

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
