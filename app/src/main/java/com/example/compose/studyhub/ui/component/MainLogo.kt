package com.example.compose.studyhub.ui.component

import androidx.compose.foundation.Image
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.res.painterResource
import com.example.compose.studyhub.R

@Composable
fun Logo(
    modifier: Modifier = Modifier,
    lightTheme: Boolean = LocalContentColor.current.luminance() < 0.5f,
) {
    val assetId = if (lightTheme) {
        R.drawable.a
    } else {
        R.drawable.a
    }
    Image(painter = painterResource(id = assetId), modifier = modifier, contentDescription = null)
}