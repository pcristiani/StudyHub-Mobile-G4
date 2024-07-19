package com.example.compose.studyhub.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.studyhub.R
import com.example.compose.studyhub.domain.EmailState
import com.example.compose.studyhub.ui.component.loginRegisterEdit.Email
import com.example.compose.studyhub.ui.theme.ThemeStudyHub

@Composable
fun DatosAsignaturaBox(
    onDismissRequest: () -> Unit,
    asignatura: String,
    carrera: String,
    descripcion: String,
    departamento: String,
    creditos: Int,
) {
    AlertDialog(
        title = {
            Column(){
                Text(text = asignatura, style = MaterialTheme.typography.h5)
                Spacer(modifier = Modifier.height(3.dp))
                Text(text = "Carrera: $carrera", style = MaterialTheme.typography.subtitle2)
            }
                },
        text = {
            Spacer(modifier = Modifier.height(10.dp))
            Card(modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(5.dp), border = BorderStroke(0.8.dp, colorResource(R.color.divider))
            ){
                Column(modifier = Modifier.padding(5.dp)){
                    Text(modifier = Modifier.padding(start = 5.dp), text = descripcion, style = MaterialTheme.typography.subtitle2)
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(modifier = Modifier.padding(start = 5.dp),text = "Departamento: $departamento", style = MaterialTheme.typography.subtitle2)
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(modifier = Modifier.padding(start = 5.dp),text = "Créditos: $creditos", style = MaterialTheme.typography.subtitle2)
                }
            }
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {},
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                },
            ) {
                Text("Cerrar")
            }
        }
    )
}

@Preview
/*@Preview(name = "Sign in dark theme", uiMode = UI_MODE_NIGHT_YES)*/
@Composable
fun DatosAsignaturaBoxPreview() {
    ThemeStudyHub {
        DatosAsignaturaBox({}, "Hello", "Hello", "Descripcion de carrera", "Matemáticas", 0)
    }
}