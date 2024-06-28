package com.example.compose.studyhub.domain

import java.util.regex.Pattern

// Formato ci "0.000.000-0"

//private const val CI_VALIDATION = "^\\d{8}\\.\\d{3}\\.\\d{3}-\\d{1}\$"
private const val CI_VALIDATION = "^\\d{8}\$"

///
class CIState(val ci: String? = null) : TextFieldState(validator = ::isCIValid, errorFor = ::ciValidationError) {
   // Si ci no es nula, se asigna al campo de texto
   init {
      ci?.let { text = it }
   }
}

///
// Devuelve msj o nulo si no se encontró ningún error
private fun ciValidationError(ci: String): String {
   return "Cédula inválida: $ci"
}

///
private fun isCIValid(ci: String): Boolean {
   return Pattern.matches(CI_VALIDATION, ci)
}

///
val CIStateSaver = textFieldStateSaver(CIState()) // // // // "grupo4.proyecto2024@gmail.com" // val nombre: String? = "Mensaje Sebastian"

