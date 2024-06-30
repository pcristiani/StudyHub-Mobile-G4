package com.example.compose.studyhub.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.studyhub.ui.theme.ThemeStudyHub

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ConfirmDialogBox(
    onDismissRequest: () -> Unit,
    dialogTitle: String,
) {
    AlertDialog(
        title = {
            Box(modifier = Modifier.padding(start=2.dp, top=2.dp)){
                Text(text = dialogTitle)

            }
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Aceptar")
            }
        }
    )
}


@Composable
fun ConfirmDialogBoxCreation(onConfirmation: () -> Unit, dialogTitle: String, dialogText: String, onDismissRequest: () -> Unit) {
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
fun ConfirmDialogueBoxPreview() {
    ThemeStudyHub {
        ConfirmDialogBox({}, "Hello")
    }
}