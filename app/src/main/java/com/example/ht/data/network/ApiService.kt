package com.example.ht.data.network

import com.example.ht.data.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface ApiService {
    @GET("users")
    suspend fun loginUser(
        @Query("username") username: String,
        @Query("password") password: String
    ): Response<List<User>>

    @POST("users")
    suspend fun registerUser(@Body user: User): Response<User>

    @GET("users")
    suspend fun getAllUsers(): Response<List<User>>
}