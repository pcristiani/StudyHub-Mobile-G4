import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

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
      registerRequest: RegisterRequest
             ): Call<String>
   
   
   @PUT("/api/usuario/modificarPerfil/{idUsuario}")
   fun modifyProfile(
      @Path("idUsuario")
      idUsuario: Int,
      @Header("Authorization")
      token: String,
      @Body
      modifyProfileRequest: ModifyProfileRequest
                    ): Call<String>
   
   
   @POST("/cerrarSesion")
   fun logout(
      @Body
      token: String
             ): Call<String>
   
   
   @GET("/api/usuario/getUsuario/{idUsuario}")
   fun getUsuario(
      @Path("idUsuario")
      idUsuario: Int,
      @Header("Authorization")
      token: String
                 ): Call<UserRequest>
   
   
   @GET("/api/usuario/getUsuarios")
   fun getUsers(
               ): Call<String>
   
   
   @GET("/api/carrera/getCarrerasInscripcionesPendientes")
   fun getInscPendientes(): Call<String>


   @GET("/api/carrera/getCarreras")
   fun getCarreras(@Header("Authorization") token: String): Call<String>

   @POST("/api/carrera/inscripcionCarrera")
   fun inscripcionCarrera(@Header("Authorization") token: String, @Body inscripcionCarreraRequest: InscripcionCarreraRequest): Call<String>

   @GET("/api/carrera/getCarrerasInscripto/{idUsuario}")
   fun getCarrerasInscripto(@Path("idUsuario") idUsuario: Int, @Header("Authorization")token:String): Call<String>

   @GET("/api/asignatura/getAsignaturasDeCarrera/{idCarrera}")
   fun getAsignaturasDeCarrera(@Path("idCarrera") idCarrera: Int, @Header("Authorization")token:String): Call<String>

   @GET("/api/asignatura/getHorarios/{idAsignatura}")
   fun getHorariosAsignatura(@Path("idAsignatura") idAsignatura: Int, @Header("Authorization")token:String): Call<String>

   @GET("/api/asignatura/getAsignaturasNoAprobadas/{idUsuario}")
   fun getAsignaturasNoAprobadas(
      @Path("idUsuario")
      idUsuario: Int,
      @Header("Authorization")
      token: String
                                ): Call<String>
   
   
   @GET("/api/asignatura/getAsignaturasAprobadas/{idUsuario}")
   fun getAsignaturasAprobadas(
      @Path("idUsuario")
      idUsuario: Int,
      @Header("Authorization")
      token: String
                              ): Call<String>
   
   
   @POST("/api/usuario/registerMobileToken/{idUsuario}")
   fun registerMobileToken(
      @Path("idUsuario")
      idUsuario: Int,
      @Body
      mobileToken: String,
      @Header("Authorization")
      token: String
                          ): Call<String>

   @GET("/api/estudiante/getCalificacionesExamenes/{idUsuario}")
   fun getCalificacionesExamenes(@Path("idUsuario") idUsuario:Int, @Query("idCarrera") idCarrera: Int, @Header("Authorization")token:String): Call<String>

   @GET("/api/estudiante/getCalificacionesAsignaturas/{idUsuario}")
   fun getCalificacionesAsignatura(@Path("idUsuario") idUsuario:Int, @Query("idCarrera") idCarrera: Int, @Header("Authorization")token:String): Call<String>



   @GET("/api/carrera/getPreviaturasGrafo/{idCarrera}")
   fun getPreviaturasGrafo(@Path("idCarrera") idCarrera:Int): Call<String>


}

data class LoginRequest(val cedula: String, val password: String)

data class RegisterRequest(val nombre: String, val apellido: String, val email: String, val fechaNacimiento: String, val cedula: String, val password: String, val rol: String = "E")

data class ModifyProfileRequest(val nombre: String, val apellido: String, val email: String, val fechaNacimiento: String)

data class TokenRequest(val token: String)

data class UserRequest(val idUsuario: Int?, val nombre: String?, val apellido: String?, val email: String?, val fechaNacimiento: String?, val rol: String?, val cedula: String?, val activo: Boolean?, val validado: Boolean?)

data class CarreraRequest(val idCarrera: Int, val nombre: String, val descripcion: String, val requisitos: String, val duracion: Int, val activa: Boolean)

data class InscripcionCarreraRequest(val idCarrera: Int, val idEstudiante: Int, val validado: Boolean)

data class AsignaturaRequest(val idAsignatura: Int, val idCarrera: Int, val nombre: String, val creditos: Int, val descripcion: String, val departamento: String, val tieneExamen: Boolean, val activa: Boolean, val previaturas: List<Int>)

data class Calificacion(val resultado: String, val calificacion: Int)

data class CalificacionExamenRequest(val idAsignatura: Int, val asignatura: String, val idExamen: Int, val resultado: String, val calificacion: Int)


data class CalificacionAsignaturaRequest(val idAsignatura: Int, val asignatura: String, val calificaciones: List<Calificacion>)

data class HorariosAsignaturaRequest(val idHorarioAsignatura: Int, val idAsignatura: Int, val anio: Int, val dtHorarioDias: List<HorariosDias>)


data class HorariosDias(val diaSemana: String, val horaInicio: String, val horaFin: String)