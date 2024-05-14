package com.example.compose.studyhub.ui.component.register

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.studyhub.R
import com.example.compose.studyhub.ui.component.questionLogin.QuestionWrapper
import com.example.compose.studyhub.ui.component.questionLogin.simpleDateFormatPattern
import com.example.compose.studyhub.ui.theme.ThemeStudyHub
import com.example.compose.studyhub.ui.theme.slightlyDeemphasizedAlpha
import com.example.compose.studyhub.util.getDefaultDateInMillis
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

@Composable
fun SelectFechaUsuario(
   @StringRes titleResourceId: Int,
   @StringRes directionsResourceId: Int,
   dateInMillis: Long?,
   onClick: () -> Unit,
   modifier: Modifier = Modifier,
) {
   QuestionWrapper(
      titleResourceId = titleResourceId,
      directionsResourceId = directionsResourceId,
      modifier = modifier,
   ) {
      val dateFormat = SimpleDateFormat(simpleDateFormatPattern, Locale.getDefault())
      dateFormat.timeZone = TimeZone.getTimeZone("UTC")
      val dateString = dateFormat.format(dateInMillis ?: getDefaultDateInMillis())

      Button(
         onClick = onClick,
         colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = slightlyDeemphasizedAlpha),
         ),
         shape = MaterialTheme.shapes.small,
         modifier = Modifier
            .padding(vertical = 20.dp)
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

/*@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)*/
@Preview(name = "Light", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun SelectFechaUsuarioPreview() {
   ThemeStudyHub {
      Surface {
         SelectFechaUsuario(
            titleResourceId = R.string.txt_question3,
            directionsResourceId = R.string.select_fecha,
            dateInMillis = 1672560000000, // 2023-01-01
            onClick = {},
         )
      }
   }
}
