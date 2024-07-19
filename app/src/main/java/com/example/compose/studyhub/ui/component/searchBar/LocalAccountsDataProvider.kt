package com.example.compose.studyhub.ui.component.searchBar

import CarreraRequest
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.compose.studyhub.R
import com.example.compose.studyhub.data.Account
import com.example.compose.studyhub.data.UserRepository
import com.example.compose.studyhub.http.requests.getCarrerasRequest
import com.example.compose.studyhub.ui.component.searchBar.LocalAccountsDataProvider.allUserAccounts
//import com.example.compose.studyhub.ui.component.searchBar.LocalAccountsDataProvider.allUserContactAccounts

object LocalAccountsDataProvider {

    val allUserAccounts = listOf(

        Account(
            idCarrera = 1,
            nombre = "Tecnologo Mecanica",
            descripcion = "Carrera terciaria de mecánica."
        ),
        Account(
            idCarrera = 2,
            nombre = "Tecnologo Biologia",
            descripcion = "Carrera terciaria de biología."
        ),
        Account(
            idCarrera = 3,
            nombre = "Tecnologo Informatica",
            descripcion = "Carrera terciaria de informática."
        ),
        Account(
            idCarrera = 4,
            nombre = "Tecnologo Quimica",
            descripcion = "Carrera terciaria de química."
        ),
    )
/*
    val allUserContactAccounts = listOf(
        Account(
            idCarrera = 5,
            nombre = "Tecnologo Electricidad",
            descripcion = "Carrera terciaria de electricidad.",
            requisitos = "Bachillerato completo.",
            duracion = 3,
            activa = true
        ),
        Account(
            idCarrera = 6,
            nombre = "Tecnologo Electronica",
            descripcion = "Carrera terciaria de electrónica.",
            requisitos = "Bachillerato completo.",
            duracion = 3,
            activa = true
        )

    )*/


    // Obtenga la cuenta predeterminada del usuario actual.
  //  fun getDefaultUserAccount() = allUserAccounts.first()


    // Si el uid dado [Account.id] es o no una cuenta propiedad del usuario actual.
 //   fun isUserAccount(nameC: String): Boolean = allUserAccounts.any { it.nombre == nameC }


    // Obtener el contacto del usuario actual con el dado [accountId].
 /*   fun getContactAccountByUid(idC: Int): Account {
        return allUserContactAccounts.first { it.idCarrera == idC }
    }*/
}
