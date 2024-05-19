package com.example.compose.studyhub.ui.estudiante

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.studyhub.R
import com.example.compose.studyhub.ui.component.registrarEstudiante.QuestionWrapper
import com.example.compose.studyhub.ui.component.registrarEstudiante.simpleDateFormatPattern
import com.example.compose.studyhub.ui.theme.slightlyDeemphasizedAlpha
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

@Composable
fun InscripcionScreen(): DrawerState {
   Column(modifier = Modifier.padding(top = 100.dp, bottom = 6.dp)) {
      SelectTipoInscripcion(titleResourceId = R.string.txt_inscripciones, directionsResourceId = R.string.txt_tipo_inscripcion, dateInMillis = "Carreras", onClick = { })
   }
   return DrawerState(DrawerValue.Closed)
}


@Composable
fun SelectTipoInscripcion(
   @StringRes
   titleResourceId: Int,
   @StringRes
   directionsResourceId: Int,
   dateInMillis: String?,
   onClick: @Composable () -> Unit,
   modifier: Modifier = Modifier,
                         ) {
   QuestionWrapper(
      titleResourceId = titleResourceId,
      directionsResourceId = directionsResourceId,
      modifier = modifier,) {
      val dateFormat = SimpleDateFormat(simpleDateFormatPattern, Locale.getDefault())
      dateFormat.timeZone = TimeZone.getTimeZone("UTC")
      val dateString = dateInMillis ?: "Seleccione el tipo de inscripción" // dateFormat.format(dateInMillis ?: getDefaultDateInMillis())
      
      
      Button(
         onClick = { onClick },
         colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
            contentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = slightlyDeemphasizedAlpha),
                                             ),
         shape = MaterialTheme.shapes.small,
         modifier = Modifier
            .padding(vertical = 10.dp)
            .height(54.dp),
         border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)),
            ) {
         Text(text = dateString, modifier = Modifier
            .fillMaxWidth()
            .weight(1.8f))
         Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = null, modifier = Modifier
            .fillMaxWidth()
            .weight(0.2f))
      }
   }
   
}


@Composable
fun Checkbox(checked: Boolean, onCheckedChange: ((Boolean) -> Unit)?, modifier: Modifier = Modifier, enabled: Boolean = true, colors: CheckboxColors = CheckboxDefaults.colors(), interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }) {
}


@Composable
fun ItemMaterial3() {
   ListItem(headlineContent = { Text("Item de tres lineas") }, overlineContent = { Text("OVERLINE") }, supportingContent = { Text("Aris el mejor") }, leadingContent = {
      Icon(
         Icons.Filled.Favorite,
         contentDescription = "Icono de corazon",
          )
      Icon(
         Icons.Filled.Favorite,
         contentDescription = "Icono de corazon",
          )
   }, trailingContent = { Text("JetpackCompose.Pro") })
}/* @Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ItemMaterial3s() {
   val scope = rememberCoroutineScope()
   val scaffoldState = rememberBottomSheetScaffoldState()
   
   BottomSheetScaffold(scaffoldState = scaffoldState, sheetPeekHeight = 128.dp, sheetContent = {
      Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
         Box(Modifier
            .fillMaxWidth()
            .height(128.dp), contentAlignment = Alignment.Center) {
            Text("Swipe up to expand sheet")
         }
         Text("Sheet content")
         Button(modifier = Modifier.padding(bottom = 64.dp), onClick = {
            scope.launch { scaffoldState.bottomSheetState.partialExpand() }
         }) {
            Text("Click to collapse sheet")
         }
      }
   }) { innerPadding ->
      Box(modifier = Modifier
         .fillMaxSize()
         .padding(innerPadding), contentAlignment = Alignment.Center) {
         Text("Inscripciones")
      }
   }
} */


/*@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)*/
@Preview(name = "Light", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun InscripcionScreenPreview() {
   InscripcionScreen()/* CheckboxRow(text = "s", selected = true, onOptionSelected = {}) */ //
}
