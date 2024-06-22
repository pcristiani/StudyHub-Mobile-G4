package com.example.compose.studyhub.ui.component.loginRegisterEdit

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.compose.studyhub.R
import com.example.compose.studyhub.domain.TextFieldState
import com.example.compose.studyhub.ui.screen.TextFieldError

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
