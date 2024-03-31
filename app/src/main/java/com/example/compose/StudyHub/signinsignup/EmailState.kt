package com.example.compose.StudyHub.signinsignup

import java.util.regex.Pattern

// Considere un correo electrónico válido si hay algún texto antes y después de un "@"
private const val EMAIL_VALIDATION_REGEX = "^(.+)@(.+)\$"

class EmailState(val email: String? = null) :
    TextFieldState(validator = ::isEmailValid, errorFor = ::emailValidationError) {
    init {
        email?.let {
            text = it
        }
    }
}

// Devuelve un error a mostrar o nulo si no se encontró ningún error
private fun emailValidationError(email: String): String {
    return "Email inválido: $email"
}

private fun isEmailValid(email: String): Boolean {
    return Pattern.matches(EMAIL_VALIDATION_REGEX, email)
}

val EmailStateSaver = textFieldStateSaver(EmailState())
