package com.example.ht.data.network
import com.example.ht.data.model.Food
import retrofit2.Call
import retrofit2.http.*

interface FoodApiService {
    // GET all food items
    @GET("foods")
    fun getAllFoods(): Call<List<Food>>

    // POST a new food item
    @POST("foods")
    fun addFood(@Body newFood: Food): Call<Food>

    // DELETE a food item by ID
    @DELETE("foods/{id}")
    fun deleteFood(@Path("id") id: Int): Call<Unit>
}