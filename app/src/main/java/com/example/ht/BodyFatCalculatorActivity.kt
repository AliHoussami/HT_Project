package com.example.ht

import android.content.Context
import android.os.Bundle
import android.widget.*
import androidx.activity.ComponentActivity
import kotlin.math.log10

class BodyFatCalculatorActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_body_fat_cal)

        val etHeight = findViewById<EditText>(R.id.etHeight)
        val etWaist = findViewById<EditText>(R.id.etWaist)
        val etNeck = findViewById<EditText>(R.id.etNeck)
        val etHip = findViewById<EditText>(R.id.etHip) // For women only
        val radioGroupGender = findViewById<RadioGroup>(R.id.radioGroupGender)
        val btnCalculateBodyFat = findViewById<Button>(R.id.btnCalculateBodyFat)
        val tvBodyFatResult = findViewById<TextView>(R.id.tvBodyFatResult)

        btnCalculateBodyFat.setOnClickListener {
            val heightInput = etHeight.text.toString()
            val waistInput = etWaist.text.toString()
            val neckInput = etNeck.text.toString()
            val hipInput = etHip.text.toString()
            val selectedGenderId = radioGroupGender.checkedRadioButtonId

            if (heightInput.isNotEmpty() && waistInput.isNotEmpty() && neckInput.isNotEmpty() && selectedGenderId != -1) {
                try {
                    val height = heightInput.toDouble()
                    val waist = waistInput.toDouble()
                    val neck = neckInput.toDouble()
                    val hip = if (selectedGenderId == R.id.radioFemale && hipInput.isNotEmpty()) hipInput.toDouble() else 0.0

                    val bodyFat = if (selectedGenderId == R.id.radioMale) {
                        // Formula for men
                        495 / (1.0324 - 0.19077 * log10(waist - neck) + 0.15456 * log10(height)) - 450
                    } else {
                        // Formula for women
                        495 / (1.29579 - 0.35004 * log10(waist + hip - neck) + 0.22100 * log10(height)) - 450
                    }

                    if (bodyFat.isNaN() || bodyFat < 0) {
                        tvBodyFatResult.text = "Invalid input. Please check values."
                    } else {
                        tvBodyFatResult.text = String.format("Your Body Fat: %.2f%%", bodyFat)
                        saveBodyFat(bodyFat)
                    }
                } catch (e: NumberFormatException) {
                    Toast.makeText(this, "Please enter valid numbers.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please complete all fields and select a gender.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveBodyFat(bodyFat: Double) {
        val sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val username = sharedPreferences.getString("LOGGED_IN_USER", null)

        if (username != null) {
            val userPrefs = getSharedPreferences("HealthMetrics_$username", Context.MODE_PRIVATE)
            val editor = userPrefs.edit()
            editor.putString("BODY_FAT_RESULT", String.format("%.2f", bodyFat))
            editor.apply()

            Toast.makeText(this, "Body Fat saved successfully for $username", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "No user logged in. Please log in first.", Toast.LENGTH_SHORT).show()
        }
    }
}
