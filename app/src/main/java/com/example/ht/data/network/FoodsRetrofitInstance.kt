package com.example.ht.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object FoodsRetrofitInstance {

    private const val BASE_URL = "http://10.0.2.2:3001/" // Replace with your actual server URL

    val apiService: FoodApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FoodApiService::class.java)
    }
}