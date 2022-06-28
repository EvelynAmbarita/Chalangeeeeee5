package com.Evelyndamayantiambarita.challengechapter5.network

import com.Evelyndamayantiambarita.challengechapter5.data.api.auth.AuthAPI
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object AuthApiClient {
    const val BASE_URL = "https://Evelyndamayantiambarita.xyz/api/"

    private val logging: HttpLoggingInterceptor
        get() {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            return httpLoggingInterceptor.apply {
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            }
        }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    val instanceAuth: AuthAPI by lazy {
        retrofit.create(AuthAPI::class.java)
    }
}