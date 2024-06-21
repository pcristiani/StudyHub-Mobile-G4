package com.example.compose.studyhub.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.example.compose.studyhub.R
import com.example.compose.studyhub.domain.EmailState
import com.example.compose.studyhub.ui.screen.Email
import com.example.compose.studyhub.ui.theme.ThemeStudyHub

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertDialogBoxWithText(
    onDismissRequest: () -> Unit,
    onConfirmation: (String) -> Unit,
    dialogTitle: String,
) {
    val emailState = remember{ EmailState() }
    AlertDialog(
        title = {
            Text(text = dialogTitle)
        },
        text = {
             Email(emailState)
        },

        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation(emailState.text)
                }
            ) {
                Text("Confirmar")
            }
        },

        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Cancelar")
            }
        }
    )
}


@Composable
fun DialogBoxWithTextCreation(onConfirmation: (String) -> Unit, dialogTitle: String, onDismissRequest: () -> Unit) {
    val openAlertDialog = remember { mutableStateOf(true) }


    when {
        openAlertDialog.value -> {
            AlertDialogBoxWithText(
                onDismissRequest = { onDismissRequest()},
                onConfirmation = {
                    openAlertDialog.value = false
                    onConfirmation(it)
                    onDismissRequest()
                },
                dialogTitle = dialogTitle
            )
        }
    }
}


@Preview
/*@Preview(name = "Sign in dark theme", uiMode = UI_MODE_NIGHT_YES)*/
@Composable
fun AlertDialogueBoxWithTextPreview() {
    ThemeStudyHub {
        AlertDialogBoxWithText({}, {}, "Hello")
    }
}