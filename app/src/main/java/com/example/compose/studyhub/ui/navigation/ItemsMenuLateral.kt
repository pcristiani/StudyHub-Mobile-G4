package com.example.compose.studyhub.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddToPhotos
import androidx.compose.material.icons.outlined.Bookmarks
import androidx.compose.material.icons.outlined.BusinessCenter
import androidx.compose.material.icons.outlined.Campaign
import androidx.compose.material.icons.outlined.Description
import androidx.compose.ui.graphics.vector.ImageVector

sealed class ItemsMenuLateral(val icon: ImageVector, val title: String, val ruta: String) {
   object ItemMenuNovedades : ItemsMenuLateral(icon = Icons.Outlined.Campaign, title = "Novedades", ruta = NavScreen.NovedadesScreen)
   object ItemMenuPlanEstudios : ItemsMenuLateral(icon = Icons.Outlined.Description, title = "Plan de estudios", ruta = NavScreen.EstudiosScreen)
   object ItemMenuInscripcion : ItemsMenuLateral(icon = Icons.Outlined.AddToPhotos, title = "Inscripciones", ruta = NavScreen.InscripcionScreen)
   object ItemMenuSolicitudes : ItemsMenuLateral(icon = Icons.Outlined.Bookmarks, title = "Solicitudes", ruta = NavScreen.SolicitudesScreen)
   object ItemMenuGestion : ItemsMenuLateral(icon = Icons.Outlined.BusinessCenter, title = "Gestión", ruta = NavScreen.GestionScreen)
}