package com.example.compose.studyhub.ui.component.searchBar

import CarrerassRequest
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.compose.studyhub.data.Account

enum class ReplyContentType {
    SINGLE_PANE, DUAL_PANE
}

@Composable
fun SearchBarScreen(emails: List<Account>,  modifier: Modifier = Modifier, navigateToDetail: (Int, ReplyContentType) -> Unit) {
    Box(modifier = modifier .fillMaxWidth()){
        SearchList(
            emails = emails,
          //  carreras = carreras,
            onSearchItemSelected = { searchedEmail ->
                navigateToDetail(searchedEmail.idCarrera, ReplyContentType.SINGLE_PANE)
            },
             modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp)
        )
    }
}
