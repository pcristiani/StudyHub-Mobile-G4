import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("/iniciarSesion")
    fun getPostById(@Path("id") postId: Int): Call<Post>
}