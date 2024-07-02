import androidx.compose.material.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import com.example.compose.studyhub.ui.navigation.NavRoutes

@Composable
fun alertDialogDoc() {
    val openDialog = remember { mutableStateOf(true) }

    if (openDialog.value) {
        AlertDialog(onDismissRequest = {
            openDialog.value = false
        }, title = {
            Text(text = "Documentación  de AlertDialog")
        }, text = {
            Text(
                "Descripción de la alerta."
            )
        }, confirmButton = {
            TextButton(onClick = {
                openDialog.value = false
            }) {
                Text("Confirmar")
            }
        }, dismissButton = {
            TextButton(onClick = {
                openDialog.value = false
            }) {
                Text("Cancelar")
            }
        })
    }
}

@Composable
fun alertDialogDoc2(title: String, text: String,onHeaderClicked: () -> Unit) {
    val openDialog = remember { mutableStateOf(true) }

    if (openDialog.value) {
        AlertDialog(onDismissRequest = {
           openDialog.value = false
        }, title = {
            Text(text = title)
        }, text = {
            Text(text)
        }, confirmButton = {
            TextButton(onClick = {
                openDialog.value = false
            }) {
                Text("Confirmar")
            }
        }, dismissButton = {
            TextButton(onClick = {
                openDialog.value = false
            }) {
                Text("Cancelar")
            }
        })
    }
}