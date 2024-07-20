package com.example.compose.studyhub.ui.component

import androidx.compose.material.AlertDialog
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.example.compose.studyhub.domain.EmailState
import com.example.compose.studyhub.ui.component.loginRegisterEdit.Email
import com.example.compose.studyhub.ui.theme.ThemeStudyHub

@Composable
fun AlertDialogBoxWithText(
    onDismissRequest: (String) -> Unit,
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
            onDismissRequest("se")
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
                    onDismissRequest("cance")
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
@Composable
fun AlertDialogueBoxWithTextPreview() {
    ThemeStudyHub {
        AlertDialogBoxWithText({}, {}, "Hello")
    }
}