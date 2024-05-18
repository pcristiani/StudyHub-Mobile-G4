package com.example.compose.studyhub.domain

// "^(.+)@(.+).com\$"  - Formato email "nameuser@dominio.com"
private const val EMAIL_VALIDATION = "^(.+)@(.+).com\$"

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
   return true
} //return Pattern.matches(EMAIL_VALIDATION, email)

///
val EmailStateSaver = textFieldStateSaver(EmailState()) // // // // "grupo4.proyecto2024@gmail.com" // val nombre: String? = "Mensaje Sebastian"

