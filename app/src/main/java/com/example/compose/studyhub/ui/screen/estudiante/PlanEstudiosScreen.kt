package com.example.compose.studyhub.ui.screen.estudiante

import CarreraRequest
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.studyhub.data.UserRepository
import com.example.compose.studyhub.http.requests.inscripcionesCarreraRequest
import com.example.compose.studyhub.ui.component.gestion.ExpandableList

@Composable
fun PlanEstudiosScreen(): DrawerState {
  //  val drawerState = rememberDrawerState(DrawerValue.Closed)
   
   /* Column(
      modifier = Modifier.fillMaxSize(),
      verticalArrangement = Arrangement.spacedBy(50.dp, Alignment.Top),
      horizontalAlignment = Alignment.CenterHorizontally,
         ) { */
      Column(modifier = Modifier.padding(top = 60.dp, start = 30.dp, end = 30.dp),
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

      ExpandableList(modifier=Modifier.padding(top = 20.dp, bottom = 1.dp), headerTitle = "Lista",
       options = nombresCarrera, optionIds = idsCarrera, onOptionSelected={selectedId -> carreraSelected.value = selectedId})
      /*
      Image(
         painter = painterResource(id = R.drawable.celebridad_512),
         modifier = Modifier.size(120.dp),
         contentDescription = "Logo"
      )
      Text("Plan de estudios", style = MaterialTheme.typography.titleMedium, color = md_theme_dark_text)
      */
   }

return DrawerState(DrawerValue.Closed)
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


@Preview
@Composable
fun PlanEstudiosScreenPreview() {
   PlanEstudiosScreen()
}
