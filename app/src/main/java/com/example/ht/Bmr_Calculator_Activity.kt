package com.example.ht

import android.content.Context
import android.os.Bundle
import android.widget.*
import androidx.activity.ComponentActivity

class Bmr_Calculator_Activity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmr_calculator)

        val etAge = findViewById<EditText>(R.id.etAge)
        val etHeight = findViewById<EditText>(R.id.etHeight)
        val etWeight = findViewById<EditText>(R.id.etWeight)
        val radioGroupGender = findViewById<RadioGroup>(R.id.radioGroupGender)
        val btnCalculateBmr = findViewById<Button>(R.id.btnCalculateBmr)
        val tvBmrResult = findViewById<TextView>(R.id.tvBmrResult)

        btnCalculateBmr.setOnClickListener {
            val ageInput = etAge.text.toString()
            val heightInput = etHeight.text.toString()
            val weightInput = etWeight.text.toString()
            val selectedGenderId = radioGroupGender.checkedRadioButtonId

            if (ageInput.isNotEmpty() && heightInput.isNotEmpty() && weightInput.isNotEmpty() && selectedGenderId != -1) {
                try {
                    val age = ageInput.toInt()
                    val height = heightInput.toDouble()
                    val weight = weightInput.toDouble()

                    val bmr = if (selectedGenderId == R.id.radioMale) {
                        // Formula for men
                        88.362 + (13.397 * weight) + (4.799 * height) - (5.677 * age)
                    } else {
                        // Formula for women
                        447.593 + (9.247 * weight) + (3.098 * height) - (4.330 * age)
                    }

                    tvBmrResult.text = String.format("Your BMR: %.2f kcal/day", bmr)
                    saveBmr(bmr)
                } catch (e: NumberFormatException) {
                    Toast.makeText(this, "Please enter valid numbers.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please complete all fields and select a gender.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveBmr(bmr: Double) {
        val sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val username = sharedPreferences.getString("LOGGED_IN_USER", null)

        if (username != null) {
            val userPrefs = getSharedPreferences("HealthMetrics_$username", Context.MODE_PRIVATE)
            val editor = userPrefs.edit()
            editor.putString("BMR_RESULT", String.format("%.2f", bmr))
            editor.apply()

            Toast.makeText(this, "BMR saved successfully for $username", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "No user logged in. Please log in first.", Toast.LENGTH_SHORT).show()
        }
    }
}
