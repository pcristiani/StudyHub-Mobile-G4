package com.example.compose.studyhub.ui.component.gestion

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.List
import androidx.compose.material.icons.rounded.ChevronLeft
import androidx.compose.material.icons.rounded.List
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateMap
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.compose.studyhub.R

@Composable
fun ExpandableList(modifier:Modifier, headerTitle: String, options: List<String>, optionIds: List<Int>, onOptionSelected: (Int) -> Unit) {

    val data = SectionData(headerTitle, options, optionIds)

    val isExpandedMap = remember { mutableStateOf(false) }


    LazyColumn(
        modifier = modifier,
        content = {
                Section(
                    sectionData = data,
                    isExpanded = isExpandedMap.value,
                    onHeaderClick = {
                        isExpandedMap.value = !(isExpandedMap.value)
                    },
                    onOptionClicked = onOptionSelected
                )
            }
    )
}



@Composable
fun SectionItem(text: String, id: Int, onOptionClicked: (id: Int) -> Unit) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .clickable{onOptionClicked(id)}
    )
}

@Composable
fun SectionHeader(text: String, isExpanded: Boolean, onHeaderClicked: () -> Unit) {
    Row(modifier = Modifier
        .clickable { onHeaderClicked() }
        .background(Color.LightGray)
        .padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.weight(1.0f)
        )
        if (isExpanded) {
            Icons.AutoMirrored.Rounded.List
        } else {
            Icons.Rounded.ChevronLeft
        }
    }
}

fun LazyListScope.Section(
    sectionData: SectionData,
    isExpanded: Boolean,
    onHeaderClick: () -> Unit,
    onOptionClicked: (id: Int) -> Unit
) {


    item {
        SectionHeader(
            text = sectionData.headerText,
            isExpanded = isExpanded,
            onHeaderClicked = onHeaderClick

        )
    }

    if(isExpanded) {
        itemsIndexed(sectionData.items) { index, item ->
            SectionItem(text = item, id =sectionData.ids[index], onOptionClicked = onOptionClicked)
        }
    }
}

data class SectionData(val headerText: String, val items: List<String>, val ids: List<Int>)