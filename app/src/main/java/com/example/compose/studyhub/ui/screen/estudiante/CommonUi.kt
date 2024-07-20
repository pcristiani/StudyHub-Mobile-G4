package com.example.compose.studyhub.ui.screen.estudiante

import CarreraRequest
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.studyhub.R
import com.example.compose.studyhub.ui.theme.md_theme_List

import java.text.DecimalFormat

@Composable
fun AccountRow(
    modifier: Modifier = Modifier,
    name: String,
    number: Int,
    amount: Float,
    color: Color
) {
  /*  BaseRow(
        modifier = modifier,
        color = color,
        title = name,
        subtitle = stringResource(R.string.txt_selectHorario) + AccountDecimalFormat.format(number),
        amount = amount,
        negative = false
    )*/
}

@Composable
fun BillRow(name: String, due: String, amount: Float, color: Color, background: Color) {
  /*  BaseRow(
        color = color,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .border(BorderStroke(1.dp, Color.DarkGray), shape = RoundedCornerShape(8.dp))
            .background(MaterialTheme.colors.surface , RoundedCornerShape(8.dp)),
        title = name,
        subtitle = "Due $due",
        amount = amount,
        negative = false
    )*/
}

@Composable
private fun BaseRow(
    modifier: Modifier = Modifier,
    color: Color,
    title: String,
    subtitle: String,
    amount: Float,
    negative: Boolean
) {
    val dollarSign = if (negative) "–$ " else "$ "
    val formattedAmount = formatAmount(amount)
    Row(
        modifier = modifier
            .height(68.dp)
            .clearAndSetSemantics {
                contentDescription =
                    "$title account ending in ${subtitle.takeLast(4)}, current balance $dollarSign$formattedAmount"
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        val typography = MaterialTheme.typography
        AccountIndicator(
            color = color,
            modifier = Modifier
        )
        Spacer(Modifier.width(12.dp))
        Column(Modifier) {
            Text(text = title, style = typography.body1)
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(text = subtitle, style = typography.subtitle1)
            }
        }
        Spacer(Modifier.weight(1f))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = dollarSign,
                style = typography.h6,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            Text(
                text = formattedAmount,
                style = typography.h6,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
        Spacer(Modifier.width(16.dp))

        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Icon(
                imageVector = Icons.Filled.ChevronRight,
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 12.dp)
                    .size(24.dp)
            )
        }
    }
    RallyDivider()
}


@Composable
private fun AccountIndicator(color: Color, modifier: Modifier = Modifier) {
    Spacer(
        modifier
            .padding(4.dp)
            .size(2.dp, 40.dp)
            .background(color = MaterialTheme.colors.primary, shape = RoundedCornerShape(1.dp))
    )
}

@Composable
fun RallyDivider(modifier: Modifier = Modifier) {
    Divider(color = MaterialTheme.colors.background, thickness = 1.dp,
        modifier = modifier)
}

fun formatAmount(amount: Float): String {
    return AmountDecimalFormat.format(amount)
}

private val AccountDecimalFormat = DecimalFormat("####")
private val AmountDecimalFormat = DecimalFormat("#,###.##")


fun <E> List<E>.extractProportions(selector: (E) -> Float): List<Float> {
    val total = this.sumOf { selector(it).toDouble() }
    return this.map { (selector(it) / total).toFloat() }
}



@Composable
fun RowCarrera(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String,
    color: Color,
) {
    Row(
        modifier = modifier
            .height(80.dp)
            .fillMaxWidth()
            .padding(8.dp)
            .border(BorderStroke(0.8.dp, Color.Gray), shape = RoundedCornerShape(8.dp))

            .background(Color.Transparent, RoundedCornerShape(8.dp))
            .clearAndSetSemantics {
            },


        verticalAlignment = Alignment.CenterVertically
    ) {
        val typography = MaterialTheme.typography
        AccountIndicator(
            color = color,
            modifier = Modifier
        )
        Spacer(Modifier.width(12.dp))

        Column(Modifier) {
            Column(Modifier) {
                Text(text = title, style = typography.body1)
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    Text(text = subtitle, style = typography.subtitle1)
                }
            }
        }
        Spacer(Modifier.weight(1f))
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Icon(
                imageVector = Icons.Filled.ChevronRight,
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 15.dp)
                    .size(25.dp)
            )
        }
    }
  //  RallyDivider()
}




@Preview
@Composable
fun PreviewAccountRow() {
    Column {
    /*    AccountRow(
            name = "Checking",
            number = 1234,
            amount = 4321.98f,
            color = MaterialTheme.colors.primary,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .border(BorderStroke(1.dp, Color.DarkGray), shape = RoundedCornerShape(8.dp))
                .background(MaterialTheme.colors.surface , RoundedCornerShape(8.dp))
        )*/
        RowCarrera(
            title = "Ingeniería de Sistemas",
            subtitle = "La duracion de la carrera es de 3 años",
            color = MaterialTheme.colors.primary,
        )
        RowCarrera(
            title = "Ingeniería de Sistemas",
            subtitle = "La duracion de la carrera es de 3 años",

            color = MaterialTheme.colors.primary,
        )

/*
        BaseRow(
            title = "Checking",
            subtitle = "1234",
            amount = 4321.98f,
            color = MaterialTheme.colors.primary,
            negative = false
        )*/
    }
/*            name = "Ingeniería de Sistemas",
            due = "Jan 31",
            amount = 0.0f,
            color = MaterialTheme.colors.primary,
            background = MaterialTheme.colors.surface
    */   // )
// }
}