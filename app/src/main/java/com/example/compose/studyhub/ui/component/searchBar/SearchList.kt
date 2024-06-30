package com.example.compose.studyhub.ui.component.searchBar

import CarreraRequest
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.studyhub.R
import com.example.compose.studyhub.data.Account
import com.example.compose.studyhub.data.Email
import com.example.compose.studyhub.data.UserRepository
import com.example.compose.studyhub.http.requests.getCarrerasRequest
import com.example.compose.studyhub.ui.navigation.ReplyProfileImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchList(
    emails: List<Account>,
  //  carreras: List<CarreraRequest>,
    onSearchItemSelected: (Account) -> Unit,
    modifier: Modifier = Modifier
) {
    var query by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    val searchResults = remember { mutableStateListOf<Account>() }

    LaunchedEffect(query) {
        searchResults.clear()
        if (query.isNotEmpty()) {
            searchResults.addAll(
                emails.filter {
                    it.nombre.startsWith(query, ignoreCase = true) ||
                        it.fullName.startsWith(query, ignoreCase = true)
                }
            )
        }
    }

    DockedSearchBar(
        modifier = modifier,

            query = query,
        onQueryChange = {
            query = it
        },
        onSearch = { active = false },
        active = active,
        onActiveChange = {
            active = it
        },
        placeholder = { Text(text = stringResource(id = R.string.search_emails)) },

        leadingIcon = {
            if (active) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(id = R.string.back_button),
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .clickable {
                            active = false
                            query = ""
                        },
                )
            } else {
                 Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(id = R.string.search),
                    modifier = Modifier.padding(start = 16.dp),
                )
            }

        },
         /*trailingIcon = {
            ReplyProfileImage(
                drawableResource = R.drawable.graduado2,
                description = stringResource(id = R.string.profile),
                modifier = Modifier
                    .padding(12.dp)
                    .size(32.dp)
            )
        },*/
    ) {
        if (searchResults.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(), contentPadding = PaddingValues(4.dp), verticalArrangement = Arrangement.spacedBy(4.dp)

            ) {
                items(items = searchResults, key = { it.idCarrera }) { email ->
                    ListItem(headlineContent = { Text(email.fullName) }, supportingContent = { Text(email.descripcion) }, leadingContent = {
                      /*  ReplyProfileImage(drawableResource = email.avatar, description = stringResource(id = R.string.profile), modifier = Modifier.size(32.dp)                        )*/
                    }, modifier = Modifier.clickable {
                        onSearchItemSelected.invoke(email)
                        query = ""
                        active = false
                        println("${email.fullName}")
                    })
                }
            }
        } else if (query.isNotEmpty()) {
            Text(
                text = stringResource(id = R.string.no_item_found), modifier = Modifier.padding(16.dp)
            )
        } else Text(
            text = stringResource(id = R.string.no_search_history), modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
fun firstLoad21(checked: Boolean): List<CarreraRequest>? {
    var carreras by remember { mutableStateOf<List<CarreraRequest>?>(null) }
    LaunchedEffect(checked) {
        UserRepository.loggedInUser()?.let { user ->
            UserRepository.getToken()?.let { token ->
                if (checked) {
                    getCarrerasRequest(token) { success ->
                        carreras = success
                    }
                }
            }
        }
    }
    return carreras
}


/*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailDetailAppBar(
    email: Email,
    isFullScreen: Boolean,
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit
) {
    TopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.onPrimary
        ),
        title = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = if (isFullScreen) Alignment.CenterHorizontally
                else Alignment.Start
            ) {
                Text(
                    text = email.subject,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    modifier = Modifier.padding(top = 3.dp),
                    text = "${email.threads.size} ${stringResource(id = R.string.txt_btn_invitado)}",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.outline
                )
            }
        },
        navigationIcon = {
            if (isFullScreen) {
                FilledIconButton(
                    onClick = onBackPressed,
                    modifier = Modifier.padding(8.dp),
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(id = R.string.back_button),
                        modifier = Modifier.size(14.dp)
                    )
                }
            }
        },
        actions = {
            IconButton(
                onClick = {  println("2seba ${email.sender.fullName}")},
            ) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = stringResource(id = R.string.txt_btn_invitado),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    )
}
*/



