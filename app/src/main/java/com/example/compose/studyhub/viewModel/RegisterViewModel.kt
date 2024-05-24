package com.example.compose.studyhub.viewModel

import RegisterRequest
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.compose.studyhub.auth.decodeJWT
import com.example.compose.studyhub.data.UserRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

///
class RegisterViewModel(private val userRepository: UserRepository) : ViewModel() {

    // Considere todos los registros exitosos
    fun signUp(nombre: String, apellido: String, email: String, fechaNacimiento:String, ci: String, password: String, onRegisterSubmitted: () -> Unit) {


            /*userRepository.login(email, password)
            val passwordState = userRepository.getPasswordEmail(email)
            if (passwordState == password) {
               println("Login correcto")
               onLoginComplete()
            } else {
               println("Password incorrecta ")
            }*/


            val registerRequest = RegisterRequest(nombre, apellido, email, fechaNacimiento, ci, password)



            RetrofitClient.api.signUp(registerRequest).enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    val responseText = response.body() // Procesar la respuesta del login



                    if (responseText != null) {
                        println(responseText)
                    }

                    if (response.isSuccessful) {

                        onRegisterSubmitted()
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {

                    println("Error: ${t.message}")
                }
            })



    }

    fun loginInvitado(onRegisterSubmitted: () -> Unit,) {
        userRepository.loginInvitado()
        onRegisterSubmitted()
    }
}

///
class RegisterViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(UserRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
