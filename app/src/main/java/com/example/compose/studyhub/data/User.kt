package com.example.compose.studyhub.data

import android.app.Activity
import android.content.Context

class Usuarios(val id: Long, val ci: String, val authenticationToken: String) {

    companion object {
        private const val AUTH_PREFS = "auth_prefs"
        private const val ID_KEY = "id"
        private const val CI_KEY = "ci"
        private const val AUTH_TOKEN_KEY = "auth_token"

        fun setLoggedInUser(activity: Activity, user: Usuarios) {
            activity.getSharedPreferences(AUTH_PREFS, Context.MODE_PRIVATE).also {
                it.edit()
                        .putLong(ID_KEY, user.id)
                        .putString(CI_KEY, user.ci)
                        .putString(AUTH_TOKEN_KEY, user.authenticationToken)
                        .apply()
            }
        }

        fun getLoggedInUser(activity: Activity): Usuarios? {
            val prefs =
                    activity.getSharedPreferences(AUTH_PREFS, Context.MODE_PRIVATE) ?: return null

            val userId = prefs.getLong(ID_KEY, 0)
            if (userId == 0L) {
                return null
            }

            return Usuarios(
                    userId,
                    prefs.getString(CI_KEY, "") ?: "",
                    prefs.getString(AUTH_TOKEN_KEY, "") ?: "",
            )
        }

        fun logout(activity: Activity) {
            activity.getSharedPreferences(AUTH_PREFS, Context.MODE_PRIVATE).also {
                it.edit().clear().apply()
            }
        }
    }
}
