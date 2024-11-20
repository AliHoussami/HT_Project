package com.example.ht

import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.content.Context


class Bmi_Activity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.card_bmi_calculator)

        val etHeight = findViewById<EditText>(R.id.etHeight)
        val etWeight = findViewById<EditText>(R.id.etWeight)
        val btnCalculateBmi = findViewById<Button>(R.id.btnCalculateBmi)
        val tvBmiResult = findViewById<TextView>(R.id.tvBmiResult)

        btnCalculateBmi.setOnClickListener {
            val heightInput = etHeight.text.toString()
            val weightInput = etWeight.text.toString()

            if (heightInput.isNotEmpty() && weightInput.isNotEmpty()) {
                try {
                    val height = heightInput.toDouble()
                    val weight = weightInput.toDouble()

                    val bmi = weight / (height * height)
                    tvBmiResult.text = String.format("Your BMI: %.2f", bmi)

                    // Save the result using saveBmi function
                    saveBmi(bmi)
                } catch (e: NumberFormatException) {
                    Toast.makeText(this, "Please enter valid numbers", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please enter both height and weight", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveBmi(bmi: Double) {
        // Get logged-in username from SharedPreferences
        val sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val username = sharedPreferences.getString("LOGGED_IN_USER", null)

        if (username != null) {
            // Use user-specific SharedPreferences for saving BMI
            val userPrefs = getSharedPreferences("HealthMetrics_$username", Context.MODE_PRIVATE)
            val editor = userPrefs.edit()
            editor.putString("BMI_RESULT", String.format("%.2f", bmi))
            editor.apply()

            Toast.makeText(this, "BMI saved successfully for $username", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "No user logged in. Please log in first.", Toast.LENGTH_SHORT).show()
        }
    }
}