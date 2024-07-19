package com.example.compose.studyhub.data

import androidx.annotation.DrawableRes

data class Account(val idCarrera: Int, val nombre: String, val descripcion: String){

    val fullName: String = "$nombre"
}

/*

Account(
idCarrera = 1,
nombre = "Tecnologo Mecanica",
descripcion = "Carrera terciaria de mecánica.",
requisitos = "Bachillerato completo.",
duracion = 3,
activa = true
),*/
