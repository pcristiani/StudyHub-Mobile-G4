package com.example.compose.studyhub.ui.component

import androidx.compose.material.AlertDialog
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.example.compose.studyhub.ui.theme.ThemeStudyHub

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AlertDialogBox(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
) {
    AlertDialog(
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
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
fun DialogBoxCreation(onConfirmation: () -> Unit, dialogTitle: String, dialogText: String, onDismissRequest: () -> Unit) {
    val openAlertDialog = remember { mutableStateOf(true) }

    when {
        openAlertDialog.value -> {
            AlertDialogBox(
                onDismissRequest = { onDismissRequest()},
                onConfirmation = {
                    openAlertDialog.value = false
                    onConfirmation()
                    onDismissRequest()
                },
                dialogTitle = dialogTitle,
                dialogText = dialogText,
            )
        }
    }
}


@Preview
/*@Preview(name = "Sign in dark theme", uiMode = UI_MODE_NIGHT_YES)*/
@Composable
fun AlertDialogueBoxPreview() {
    ThemeStudyHub {
        AlertDialogBox({}, {}, "Hello", "Hello")
    }
}