package by.wlad.koshelev.apexlegendstracker.GamerStats

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import java.util.concurrent.TimeUnit

interface _ApiGamerStats {


    // https://public-api.tracker.gg/v2/apex/standard/profile/origin/XaXoLOL?TRN-Api-Key=a854565a-9a99-427d-b6aa-7e45be3713e2
    @Headers("TRN-Api-Key: a854565a-9a99-427d-b6aa-7e45be3713e2")
    @GET("{platform}/{nickName}")
    suspend fun getGamerStats(
        @Path("platform") platform: String,
        @Path("nickName") nickName: String
    ): _GamerStats

    companion object {
        fun create(): _ApiGamerStats {
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
            return retrofit.create(_ApiGamerStats::class.java)
        }
    }
}