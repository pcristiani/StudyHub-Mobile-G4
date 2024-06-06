import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
   
   @Headers(
      "Accept: */*", "Content-Type: application/json"
           )


   @POST("/iniciarSesion")
   fun login(
      @Body
      loginRequest: LoginRequest
            ): Call<String>

   @POST("/registerUsuario")
   fun signUp(
       @Body
       registerRequest: RegisterRequest): Call<String>

   @GET("/api/usuario/getUsuarios")
   fun getUsers(
   ): Call<String>

   @GET("/api/carrera/getCarrerasInscripcionesPendientes")
   fun getInscPendientes(): Call<String>

   @POST("/api/usuario/registerMobileToken/{idUsuario}")
   fun registerMobileToken(@Path("idUsuario") idUsuario: Int, @Body mobileToken: String, @Header("Authorization") token: String): Call<String>
}


data class LoginRequest(val cedula: String, val password: String)

data class RegisterRequest(val nombre: String, val apellido: String, val email: String, val fechaNacimiento: String, val cedula: String, val password: String, val rol: String="E")


data class TokenRequest(val token: String)

