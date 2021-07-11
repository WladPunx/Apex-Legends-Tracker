package by.wlad.koshelev.apexlegendstracker

import by.wlad.koshelev.apexlegendstracker.GamerStats._GamerStats
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface ApiGamerStats {


    // https://public-api.tracker.gg/v2/apex/standard/profile/origin/XaXoLOL?TRN-Api-Key=a854565a-9a99-427d-b6aa-7e45be3713e2
    @GET("{platform}/{nickName}")
    suspend fun getGamerStats(
        @Path("platform") platform: String,
        @Path("nickName") nickName: String,
        @Query("TRN-Api-Key") apiKey: String = "a854565a-9a99-427d-b6aa-7e45be3713e2"
    ): _GamerStats


    companion object {
        fun create(): ApiGamerStats {
            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build()
            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl("https://public-api.tracker.gg/v2/apex/standard/profile/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
            return retrofit.create(ApiGamerStats::class.java)
        }
    }
}