package com.example.compose.studyhub.ui.component

import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


fun errorSnackbar(error: String, snackbarHostState: SnackbarHostState, scope: CoroutineScope): Job {
    return scope.launch{
        snackbarHostState.showSnackbar(message = error, actionLabel = "Cerrar", duration = SnackbarDuration.Short)
    }
}

fun adviceSnackbar(error: String, snackbarHostState: SnackbarHostState, scope: CoroutineScope): Job {
    return scope.launch{
        snackbarHostState.showSnackbar(message = error, actionLabel = "Cerrar", duration = SnackbarDuration.Short)
    }
}

fun successSnackbar(error: String, snackbarHostState: SnackbarHostState, scope: CoroutineScope): Job {
    return scope.launch{
        snackbarHostState.showSnackbar(message = error, actionLabel = "Cerrar", duration = SnackbarDuration.Short)
    }
}

