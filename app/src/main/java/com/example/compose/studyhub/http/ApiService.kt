import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {
   
   @Headers(
      "Accept: */*", "Content-Type: application/json"
           )


   @POST("iniciarSesion")
   fun login(
      @Body
      loginRequest: LoginRequest
            ): Call<String>

   @POST("registerUsuario")
   fun signUp(
       @Body
       registerRequest: RegisterRequest): Call<String>

   @GET("/api/usuario/getUsuarios")
   fun getUsers(
   ): Call<String>
}


data class LoginRequest(val cedula: String, val password: String)

data class RegisterRequest(val nombre: String, val apellido: String, val email: String, val fechaNacimiento: String, val cedula: String, val password: String, val rol: String="E")



