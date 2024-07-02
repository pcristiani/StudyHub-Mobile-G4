package com.example.compose.studyhub.util.services

import android.content.Context
import android.content.SharedPreferences
import com.example.compose.studyhub.data.User
import com.example.compose.studyhub.data.UserRepository
import com.example.compose.studyhub.http.requests.registerTokenRequest
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class PushNotificationService: FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
    }

    fun requestNewToken() {
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val newToken = task.result
                    println("New token: $newToken")
                    // Save the token to SharedPreferences
                    val sharedPreferences = getSharedPreferences("_", MODE_PRIVATE)
                    sharedPreferences.edit().putString("fb", newToken).apply()
                } else {
                    println("Fetching FCM registration token failed: ${task.exception}")
                }
            }
    }

    fun getToken(context: Context): String? {
        return context.getSharedPreferences("_", MODE_PRIVATE).getString("fb", "empty")
    }

    private fun saveTokenToPrefs(token: String) {
        val sharedPreferences: SharedPreferences = getSharedPreferences("_", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("fb", token)
        editor.apply()
    }

    companion object {
        fun getToken(context: Context): String?{
            return context.getSharedPreferences("_", MODE_PRIVATE).getString("fb", "empty")
        }
        fun requestNewToken(context: Context) {
            FirebaseMessaging.getInstance().token
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val newToken = task.result
                        println("New token: $newToken")
                        // Save the token to SharedPreferences
                        val sharedPreferences = context.getSharedPreferences("_", MODE_PRIVATE)
                        sharedPreferences.edit().putString("fb", newToken).apply()

                        println(UserRepository.loggedInUser())
                        UserRepository.loggedInUser()?.let { registerTokenRequest(it, newToken) }
                    } else {
                        println("Fetching FCM registration token failed: ${task.exception}")
                    }
                }
        }
    }
}