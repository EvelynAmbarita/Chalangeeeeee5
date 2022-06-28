package com.Evelyndamayantiambarita.challengechapter5.data.api.auth

import com.Evelyndamayantiambarita.myapplication.data.api.auth.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.POST

interface AuthAPI {
    @POST("login")
    suspend fun signIn(@Body data: SignInRequest): Response<SignInResponse>

    @POST("register")
    suspend fun signUp(@Body request: SignUpRequest): Response<SignUpResponse>

    @GET("users/logout")
    suspend fun logout(@HeaderMap headers: Map<String, String>): Response<LogoutResponse>
}