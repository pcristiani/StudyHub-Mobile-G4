package com.example.compose.studyhub.util

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

///
// Soporte de pantalla panorámica, ancho de contenido máximo 840 dp, centrado horizontalmente.
fun Modifier.supportWideScreen() =
      this.fillMaxWidth()
            .wrapContentWidth(align = Alignment.CenterHorizontally)
            .widthIn(max = 840.dp)
