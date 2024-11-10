package com.example.ht

import android.os.Bundle
import android.widget.*
import androidx.activity.ComponentActivity
import kotlin.math.log10
import kotlin.math.max

class BodyFatCalculatorActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_body_fat_cal)

        // References to UI elements
        val etAge = findViewById<EditText>(R.id.etAge)
        val etHeight = findViewById<EditText>(R.id.etHeight)
        val etWeight = findViewById<EditText>(R.id.etWeight)
        val etWaist = findViewById<EditText>(R.id.etWaist)
        val etNeck = findViewById<EditText>(R.id.etNeck)
        val etHip = findViewById<EditText>(R.id.etHip) // Only for women
        val radioGroupGender = findViewById<RadioGroup>(R.id.radioGroupGender)
        val btnCalculateBodyFat = findViewById<Button>(R.id.btnCalculateBodyFat)
        val tvBodyFatResult = findViewById<TextView>(R.id.tvBodyFatResult)

        // Set up button click listener
        btnCalculateBodyFat.setOnClickListener {
            // Get input values
            val heightInput = etHeight.text.toString()
            val waistInput = etWaist.text.toString()
            val neckInput = etNeck.text.toString()
            val hipInput = etHip.text.toString() // Only used for women
            val selectedGenderId = radioGroupGender.checkedRadioButtonId

            // Validate that inputs are filled
            if (heightInput.isNotEmpty() && waistInput.isNotEmpty() && neckInput.isNotEmpty() && selectedGenderId != -1) {
                try {
                    val height = heightInput.toDouble()
                    val waist = waistInput.toDouble()
                    val neck = neckInput.toDouble()
                    val hip = if (selectedGenderId == R.id.radioFemale && hipInput.isNotEmpty()) hipInput.toDouble() else 0.0

                    // Calculate body fat percentage based on gender
                    val bodyFat = if (selectedGenderId == R.id.radioMale) {
                        // For men: Ensure waist > neck to avoid negative values in log10
                        if (waist > neck && height > 0) {
                            86.010 * log10(waist - neck) - 70.041 * log10(height) + 36.76
                        } else {
                            Double.NaN
                        }
                    } else {
                        // For women: Ensure waist + hip > neck
                        if (waist + hip > neck && height > 0) {
                            163.205 * log10(waist + hip - neck) - 97.684 * log10(height) - 78.387
                        } else {
                            Double.NaN
                        }
                    }

                    // Check if the result is valid and non-negative
                    if (bodyFat.isNaN() || bodyFat < 0) {
                        tvBodyFatResult.text = "Calculation error: Check input values."
                    } else {
                        // Display the result with a minimum threshold to avoid unrealistic low values
                        tvBodyFatResult.text = String.format("Your Body Fat: %.2f%%", max(bodyFat, 2.0))
                    }
                } catch (e: NumberFormatException) {
                    Toast.makeText(this, "Please enter valid numbers.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please complete all fields and select a gender.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
