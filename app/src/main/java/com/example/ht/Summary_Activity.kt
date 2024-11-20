package com.example.ht

import android.content.Context
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity

class Summary_Activity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info_summary)

        val tvBmiResult = findViewById<TextView>(R.id.tvBmiResult)
        val tvBmrResult = findViewById<TextView>(R.id.tvBmrResult)
        val tvBodyFatResult = findViewById<TextView>(R.id.tvBodyFatResult)

        // Retrieve the logged-in username
        val sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val username = sharedPreferences.getString("LOGGED_IN_USER", null)

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
