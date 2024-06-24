import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

/* @SuppressLint("RestrictedApi") object RetrofitClient {
   
   const val BASE_URL = "http://localhost:8080/"
   val instance: Retrofit by lazy {
      Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
   }
   val api: ApiService by lazy {
      instance.create(ApiService::class.java)
   }
} *//*  val retrofit: Retrofit by lazy {
    Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
 } */




object RetrofitClient {

   //private const val BASE_URL = "http://10.0.2.2:8080/"
   private const val BASE_URL = "https://studyhub-backend-production.up.railway.app"

   val instance: Retrofit by lazy {
      Retrofit.Builder()
         .baseUrl(BASE_URL)
         .addConverterFactory(ScalarsConverterFactory.create())
         .addConverterFactory(GsonConverterFactory.create())
         .build()
   }
   val api: ApiService by lazy {
      instance.create(ApiService::class.java)
   }
}