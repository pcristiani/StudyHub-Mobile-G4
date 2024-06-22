package com.example.compose.studyhub.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.example.compose.studyhub.Destinations
import com.example.compose.studyhub.R
import com.example.compose.studyhub.ui.route.LogoutRoute

@Composable
fun LogoutBox(navController: NavHostController, onDismiss: () -> Unit) {
    LogoutRoute(
        onConfirmation = {
            println("Acá estoy")
            navController.navigate(Destinations.INICIO_ROUTE) {
                popUpTo(navController.graph.startDestinationId) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }

            onDismiss()
        }, dialogTitle = stringResource(id = R.string.Logout_title), dialogText = stringResource(id = R.string.Logout_question), onDismiss = onDismiss
    )
}