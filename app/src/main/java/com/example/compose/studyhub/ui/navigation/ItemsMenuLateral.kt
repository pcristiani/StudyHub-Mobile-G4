package com.example.compose.studyhub.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CreditCard
import androidx.compose.material.icons.outlined.RealEstateAgent
import androidx.compose.ui.graphics.vector.ImageVector

sealed class ItemsMenuLateral(val icon: ImageVector, val title: String, val ruta: String) {
   object ItemMenuNovedades : ItemsMenuLateral(icon = Icons.Outlined.RealEstateAgent, title = "Novedades", ruta = NavScreen.NovedadesScreen)
   object ItemMenuPlanEstudios : ItemsMenuLateral(icon = Icons.Outlined.RealEstateAgent, title = "Plan de estudios", ruta = NavScreen.EstudiosScreen)
   object ItemMenuInscripcion : ItemsMenuLateral(icon = Icons.Outlined.CreditCard, title = "Inscripciones", ruta = NavScreen.InscripcionScreen)
   object ItemMenuSolicitudes : ItemsMenuLateral(icon = Icons.Outlined.RealEstateAgent, title = "Solicitudes", ruta = NavScreen.SolicitudesScreen)
   object ItemMenuGestion : ItemsMenuLateral(icon = Icons.Outlined.RealEstateAgent, title = "Gestión", ruta = NavScreen.GestionScreen)
}/*
sealed class ItemsMenuLateral(val icon: ImageVector, val title: String, val ruta: String) {
   object ItemMenuPlanEstudios : ItemsMenuLateral(Icons.Outlined.CreditCard, "Plan de estudios", NavScreen.EstudiosScreen)
   object ItemMenuInscripcion : ItemsMenuLateral(Icons.Outlined.RealEstateAgent, "Inscripciones", NavScreen.InscripcionScreen)
   object ItemMenuSettings : ItemsMenuLateral(Icons.Outlined.RealEstateAgent, "Solicitudes", NavScreen.SolicitudesScreen)
   object ItemMenuAbout : ItemsMenuLateral(Icons.Outlined.RealEstateAgent, "Gestión", NavScreen.GestionScreen)

}

*/
