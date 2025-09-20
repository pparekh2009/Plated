package com.priyanshparekh.core.network

import android.util.Log
import com.priyanshparekh.core.utils.SharedPrefManager
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val client = OkHttpClient.Builder()
        .cache(null)
        .addInterceptor { chain ->
            val token = SharedPrefManager.getToken()
            Log.d("TAG", "retrofitInstance: client: token: $token")
            val request = chain.request().newBuilder()
                .header("Cache-Control", "no-cache")
                .header("Pragma", "no-cache")
                .header("Authorization", "Bearer $token")
                .build()

            chain.proceed(request)
        }
        .build()

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(ApiService::class.java)
    }
}