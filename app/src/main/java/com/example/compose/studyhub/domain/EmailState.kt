package com.example.compose.studyhub.domain

import java.util.regex.Pattern

// "^(.+)@(.+).com\$"  - Formato email "nameuser@dominio.com"
private const val EMAIL_VALIDATION = "^(.+)@(.+).(.+)\$"

///
class EmailState(val email: String? = null) : TextFieldState(validator = ::isEmailValid, errorFor = ::emailValidationError) {
   // Si email no es nulo, se asigna al campo de texto
   init {
      email?.let { text = it }
   }
}

///
// Devuelve msj o nulo si no se encontró ningún error
private fun emailValidationError(email: String): String {
   return "Email inválido: $email"
}

///
private fun isEmailValid(email: String): Boolean {
return Pattern.matches(EMAIL_VALIDATION, email)
}

///
val EmailStateSaver = textFieldStateSaver(EmailState()) // // // // "grupo4.proyecto2024@gmail.com" // val nombre: String? = "Mensaje Sebastian"

