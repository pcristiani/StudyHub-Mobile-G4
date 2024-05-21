import retrofit2.Call
import retrofit2.http.Body
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
}


data class LoginRequest(val cedula: String, val password: String)

