package com.example.compose.studyhub.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun NavigationDemoTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
   val colors = if (darkTheme) {/*    LightColorPalette*/
   } else { //LightColorPalette
   }

   MaterialTheme(typography = Typography, shapes = Shapes, content = content)
}
