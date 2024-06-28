package com.example.compose.studyhub.ui.component.searchBar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.studyhub.data.Email

enum class ReplyContentType {
    SINGLE_PANE, DUAL_PANE
}

@Composable
fun SearchBarScreen(
    emails: List<Email>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Long, ReplyContentType) -> Unit
) {
    Box(modifier = modifier
        .fillMaxWidth()
    ){
        SearchList(
            emails = emails,
            onSearchItemSelected = { searchedEmail ->
                navigateToDetail(searchedEmail.id, ReplyContentType.SINGLE_PANE)
            },
             modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 6.dp)
        )
    }
}


@Preview
@Composable
fun SearchBarScreenPreview() {
    SearchBarScreen(
        emails = LocalEmailsDataProvider.allEmails,
        modifier = Modifier,
        navigateToDetail = { _, _ -> }
    )
}