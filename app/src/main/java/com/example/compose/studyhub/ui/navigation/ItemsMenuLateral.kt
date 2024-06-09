package com.example.compose.studyhub.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AdsClick
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Campaign
import androidx.compose.material.icons.outlined.Description
import androidx.compose.ui.graphics.vector.ImageVector

sealed class ItemsMenuLateral(val icon: ImageVector, val title: String, val ruta: String) { object ItemMenuNovedades: ItemsMenuLateral(icon = Icons.Outlined.Campaign, title = "Novedades", ruta = NavRoutes.NovedadesScreen)
   object ItemMenuPlanEstudios: ItemsMenuLateral(icon = Icons.Outlined.Description, title = "Plan de estudios", ruta = NavRoutes.EstudiosScreen)
   object ItemMenuInscripcion: ItemsMenuLateral(icon = Icons.Outlined.CalendarMonth, title = "Inscripciones", ruta = NavRoutes.InscripcionScreen)
   object ItemMenuSolicitudes: ItemsMenuLateral(icon = Icons.Outlined.AdsClick, title = "Asignaturas", ruta = NavRoutes.SolicitudesScreen)
   object ItemMenuGestion: ItemsMenuLateral(icon = Icons.Outlined.Book, title = "Escolaridad", ruta = NavRoutes.GestionScreen)
}