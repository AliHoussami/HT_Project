package com.example.ht

import android.os.Bundle
import android.widget.*
import androidx.activity.ComponentActivity

class Bmr_Calculator_Activity : ComponentActivity(){
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
            // Get age, height, and weight inputs as strings
            val ageInput = etAge.text.toString()
            val heightInput = etHeight.text.toString()
            val weightInput = etWeight.text.toString()
            val selectedGenderId = radioGroupGender.checkedRadioButtonId

            // Validate input
            if (ageInput.isNotEmpty() && heightInput.isNotEmpty() && weightInput.isNotEmpty() && selectedGenderId != -1) {
                try {
                    val age = ageInput.toInt()
                    val height = heightInput.toDouble()
                    val weight = weightInput.toDouble()

                    // Check selected gender
                    val bmr = if (selectedGenderId == R.id.radioMale) {
                        // BMR formula for males
                        88.362 + (13.397 * weight) + (4.799 * height) - (5.677 * age)
                    } else {
                        // BMR formula for females
                        447.593 + (9.247 * weight) + (3.098 * height) - (4.330 * age)
                    }

                    // Display the result
                    tvBmrResult.text = String.format("Your BMR: %.2f kcal/day", bmr)
                } catch (e: NumberFormatException) {
                    Toast.makeText(this, "Please enter valid numbers", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please complete all fields and select a gender", Toast.LENGTH_SHORT).show()
            }
        }
    }
}