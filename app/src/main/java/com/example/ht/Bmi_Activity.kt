package com.example.ht

import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast


class Bmi_Activity : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.card_bmi_calculator)

        val etHeight = findViewById<EditText>(R.id.etHeight)
        val etWeight = findViewById<EditText>(R.id.etWeight)
        val btnCalculateBmi = findViewById<Button>(R.id.btnCalculateBmi)
        val tvBmiResult = findViewById<TextView>(R.id.tvBmiResult)

        btnCalculateBmi.setOnClickListener {
            // Get height and weight inputs as strings
            val heightInput = etHeight.text.toString()
            val weightInput = etWeight.text.toString()

            // Validate input
            if (heightInput.isNotEmpty() && weightInput.isNotEmpty()) {
                try {
                    val height = heightInput.toDouble()
                    val weight = weightInput.toDouble()

                    // Calculate BMI
                    val bmi = weight / (height * height)

                    // Display the result
                    tvBmiResult.text = String.format("Your BMI: %.2f", bmi)
                } catch (e: NumberFormatException) {
                    Toast.makeText(this, "Please enter valid numbers", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please enter both height and weight", Toast.LENGTH_SHORT).show()
            }
        }
    }
}