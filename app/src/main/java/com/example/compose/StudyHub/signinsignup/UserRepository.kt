
package com.example.compose.StudyHub.signinsignup

import androidx.compose.runtime.Immutable

sealed class User {
    @Immutable
    data class LoggedInUser(val email: String) : User()
    object GuestUser : User()
    object NoUserLoggedIn : User()
}

// Repositorio que contiene el usuario registrado.
// En una aplicación de producción, esta clase también manejaría la comunicación con el backend para iniciar sesión y registrarse.
object UserRepository {

    private var _user: User = User.NoUserLoggedIn
    val user: User
        get() = _user

    @Suppress("UNUSED_PARAMETER")
    fun signIn(email: String, password: String) {
        _user = User.LoggedInUser(email)
    }

    @Suppress("UNUSED_PARAMETER")
    fun signUp(email: String, password: String) {
        _user = User.LoggedInUser(email)
    }

    fun signInAsGuest() {
        _user = User.GuestUser
    }

    fun isKnownUserEmail(email: String): Boolean {
        // if the email contains "sign up" we consider it unknown
        return !email.contains("signup")
    }
}
