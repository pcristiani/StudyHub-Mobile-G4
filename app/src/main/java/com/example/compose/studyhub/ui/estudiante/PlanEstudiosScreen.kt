package com.example.compose.studyhub.ui.estudiante

import CarreraRequest
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.studyhub.R
import com.example.compose.studyhub.data.UserRepository
import com.example.compose.studyhub.http.requests.inscripcionesCarreraRequest
import com.example.compose.studyhub.ui.component.gestion.ExpandableList
import com.example.compose.studyhub.ui.theme.md_theme_dark_text

@Composable
fun PlanEstudiosScreen(): DrawerState {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
   
   Column(
      modifier = Modifier.fillMaxSize(),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally
         ) {


      var listaCarreras: List<CarreraRequest>? = null
      val nombresCarrera = remember { mutableStateListOf<String>() }
      val idsCarrera = remember { mutableStateListOf<Int>() }

      UserRepository.loggedInUser()?.let { idUsuario -> UserRepository.getToken()
         ?.let {token -> inscripcionesCarreraRequest(idUsuario, token){success->
            if(success!=null){
               listaCarreras = success
               println(success)
               nombresCarrera.clear()
               idsCarrera.clear()
               listaCarreras?.forEach {
                  nombresCarrera.add(it.nombre)
                  idsCarrera.add(it.idCarrera)
                  println(listaCarreras)
               }
            }
         } } }

      var carreraSelected = remember { mutableIntStateOf(0) }


      ExpandableList(modifier=Modifier.padding(top = 20.dp, bottom = 5.dp), headerTitle = "Lista", options = nombresCarrera, optionIds = idsCarrera, onOptionSelected={selectedId -> carreraSelected.value = selectedId})


      Image(
         painter = painterResource(id = R.drawable.celebridad_512),
         modifier = Modifier.size(120.dp),
         contentDescription = "Logo"
      )
      Text("Plan de estudios", style = MaterialTheme.typography.titleMedium, color = md_theme_dark_text)
      //
   }

return DrawerState(DrawerValue.Closed)
}

fun DropdownMenuItem(text: @Composable () -> Unit, onClick: () -> Unit, leadingIcon: @Composable () -> Unit) {

}

@Composable
fun TextButton(
   onClick: () -> Unit,
   modifier: Modifier = Modifier,
   enabled: Boolean = true,
   shape: androidx.compose.ui.graphics.Shape = ButtonDefaults.textShape,
   colors: ButtonColors = ButtonDefaults.textButtonColors(),
   elevation: ButtonElevation? = null,
   border: BorderStroke? = null,
   contentPadding: PaddingValues = ButtonDefaults.TextButtonContentPadding,
   interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
   content: @Composable RowScope.() -> Unit
              ) {
}


@Composable
fun DropdownMenuExamples() {
   var expanded by remember { mutableStateOf(true) }
   val items = listOf("Option 1", "Option 2", "Option 3")
   var selectedOption by remember { mutableStateOf(items[0]) }
   
   Column(
      modifier = Modifier.padding(16.dp)
         ) {
      Button(
         onClick = { expanded = true },
         colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
                                             ),
         modifier = Modifier
            .fillMaxWidth()
            .height(54.dp)
            ) {
         Text(
            text = selectedOption,
            modifier = Modifier.weight(1f)
             )
         Icon(
            imageVector = Icons.Filled.ArrowDropDown,
            contentDescription = null,
            modifier = Modifier.weight(0.2f)
             )
      }
      DropdownMenuItem(
         text = { Text("Send Feedback") },
         onClick = { /* Handle send feedback! */ },
         leadingIcon = { Icon(Icons.Outlined.Email, contentDescription = null) },
     
                      )
   }
}


@Composable
fun DropdownMenuItem(onClick: () -> Unit, interactionSource: @Composable () -> Unit) {
   DropdownMenuExamples(
 
         )
   
}


@Preview
@Composable
fun PlanEstudiosScreenPreview() {
   PlanEstudiosScreen()
}
