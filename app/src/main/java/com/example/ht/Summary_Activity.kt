package com.example.ht

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.ht.databinding.ActivityUserInfoSummaryBinding

class Summary_Activity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityUserInfoSummaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val tvBmiResult = binding.tvBmiResult
        val tvBmrResult = binding.tvBmrResult
        val tvBodyFatResult = binding.tvBodyFatResult
        val tvCaloriesToday = binding.tvCaloriesToday
        val tvHeight = binding.tvHeight
        val tvWeight = binding.tvWeight
        // TextView for Calories Today

        // Retrieve the logged-in username
        val sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val username = sharedPreferences.getString("LOGGED_IN_USER", null)

        // Retrieve total calories from SharedPreferences
        val foodPrefs = getSharedPreferences("FoodPrefs", Context.MODE_PRIVATE)
        val totalCaloriesToday = foodPrefs.getInt("totalCalories", 0)

        // Update the Calories Today display
        tvCaloriesToday.text = "Calories Today: $totalCaloriesToday"

        val userPrefs = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val height = userPrefs.getString("Height", "N/A")
        val weight = userPrefs.getString("Weight", "N/A")

        tvHeight.text = "Your Height = $height cm"
        tvWeight.text = "Your Weight = $weight kg"

        // If the user is logged in, retrieve their health metrics
        if (username != null) {
            // Access user-specific SharedPreferences
            val userPrefs = getSharedPreferences("HealthMetrics_$username", Context.MODE_PRIVATE)

            // Retrieve health metrics
            val bmiResult = userPrefs.getString("BMI_RESULT", "N/A")
            val bmrResult = userPrefs.getString("BMR_RESULT", "N/A")
            val bodyFatResult = userPrefs.getString("BODY_FAT_RESULT", "N/A")


            // Update UI with retrieved metrics
            tvBmiResult.text = "Your BMI: $bmiResult"
            tvBmrResult.text = "Your BMR: $bmrResult kcal/day"
            tvBodyFatResult.text = "Your Body Fat: $bodyFatResult%"

        } else {
            // Handle the case where no user is logged in
            Toast.makeText(this, "No user logged in. Please log in first.", Toast.LENGTH_SHORT).show()
            tvBmiResult.text = "Your BMI: N/A"
            tvBmrResult.text = "Your BMR: N/A"
            tvBodyFatResult.text = "Your Body Fat: N/A"

        }
    }
}