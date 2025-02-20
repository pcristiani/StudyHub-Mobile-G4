package com.example.compose.studyhub.domain

///
class PasswordState :
        TextFieldState(validator = ::isPasswordValid, errorFor = ::passwordValidationError)

///
class ConfirmPasswordState(private val passwordState: PasswordState) : TextFieldState() {
    override val isValid
        get() = passwordAndConfirmationValid(passwordState.text, text)

    override fun getError(): String? {
        return if (showErrors()) {
            passwordConfirmationError()
        } else {
            null
        }
    }
}

///
private fun passwordAndConfirmationValid(password: String, confirmedPassword: String): Boolean {
    return isPasswordValid(password) && password == confirmedPassword
}
// return password == confirmedPassword

///
private fun isPasswordValid(password: String): Boolean {
    return password.length > 2
}

///
@Suppress("UNUSED_PARAMETER")
private fun passwordValidationError(password: String): String {
    return "Contraseña no válida"
}

///
private fun passwordConfirmationError(): String {
    return "Las contraseñas no coinciden"
}
