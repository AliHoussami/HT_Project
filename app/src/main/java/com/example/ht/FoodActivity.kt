package com.example.ht

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ht.data.model.Food
import com.example.ht.data.network.FoodsRetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FoodActivity : ComponentActivity() {

    private lateinit var foodRecyclerView: RecyclerView
    private lateinit var foodAdapter: FoodAdapter
    private lateinit var totalCaloriesText: TextView
    private lateinit var sharedPreferences: SharedPreferences

    private var totalCalories = 0
    private var currentUsername: String? = null
    private val selectedFoods = mutableSetOf<String>() // Track selected foods

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food)

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

        // Retrieve the logged-in user's username
        currentUsername = sharedPreferences.getString("LOGGED_IN_USER", null)
        println("DEBUG: Retrieved LOGGED_IN_USER -> $currentUsername")

        if (currentUsername == null) {
            Toast.makeText(this, "User not logged in!", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Load previously saved foods and calories
        totalCalories = loadCalories(currentUsername!!)
        selectedFoods.addAll(loadSelectedFoods(currentUsername!!))

        // Initialize views
        foodRecyclerView = findViewById(R.id.recycler_view_food)
        totalCaloriesText = findViewById(R.id.text_total_calories)
        updateTotalCalories()

        // Set up RecyclerView
        foodAdapter = FoodAdapter(selectedFoods) { food, isChecked ->
            handleFoodSelection(food, isChecked)
        }
        foodRecyclerView.layoutManager = LinearLayoutManager(this)
        foodRecyclerView.adapter = foodAdapter

        // Fetch and display food items
        fetchFoodItems()
        val resetButton: Button = findViewById(R.id.button_reset_calories)
        resetButton.setOnClickListener {
            resetSelections()
        }
    }

    private fun fetchFoodItems() {
        FoodsRetrofitInstance.apiService.getAllFoods().enqueue(object : Callback<List<Food>> {
            override fun onResponse(call: Call<List<Food>>, response: Response<List<Food>>) {
                if (response.isSuccessful) {
                    foodAdapter.submitList(response.body())
                } else {
                    Toast.makeText(this@FoodActivity, "Failed to load foods", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Food>>, t: Throwable) {
                Toast.makeText(this@FoodActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun handleFoodSelection(food: Food, isChecked: Boolean) {
        if (isChecked) {
            selectedFoods.add(food.name) // Add food to selected list
            totalCalories += food.calories
        } else {
            selectedFoods.remove(food.name) // Remove food from selected list
            totalCalories -= food.calories
        }

        saveCalories(currentUsername!!, totalCalories)
        saveSelectedFoods(currentUsername!!, selectedFoods)
        updateTotalCalories()
    }

    private fun updateTotalCalories() {
        totalCaloriesText.text = "Total Calories: $totalCalories"
    }

    private fun saveCalories(username: String, calories: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt("calories_$username", calories)
        editor.apply()
    }

    private fun loadCalories(username: String): Int {
        return sharedPreferences.getInt("calories_$username", 0)
    }

    private fun saveSelectedFoods(username: String, foods: Set<String>) {
        val editor = sharedPreferences.edit()
        editor.putStringSet("selected_foods_$username", foods) // Save selected foods
        editor.apply()
    }

    private fun loadSelectedFoods(username: String): Set<String> {
        return sharedPreferences.getStringSet("selected_foods_$username", emptySet()) ?: emptySet()
    }
    private fun resetSelections() {
        // Clear total calories for the user
        saveCalories(currentUsername!!, 0)

        // Clear selected foods for the user
        saveSelectedFoods(currentUsername!!, emptySet())

        // Reset UI
        selectedFoods.clear() // Clear the in-memory selectedFoods
        totalCalories = 0
        updateTotalCalories()
        foodAdapter.notifyDataSetChanged() // Notify the adapter to refresh the UI

        Toast.makeText(this, "Selections and calories reset!", Toast.LENGTH_SHORT).show()
    }


}