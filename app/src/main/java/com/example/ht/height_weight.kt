package com.example.ht

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.ComponentActivity

class height_weight : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.height_weight)

        // Initialize UI elements
        val etHeight = findViewById<EditText>(R.id.etHeight)
        val etWeight = findViewById<EditText>(R.id.etWeight)
        val radioGroupGender = findViewById<RadioGroup>(R.id.radioGroupGender)
        val saveButton = findViewById<Button>(R.id.savebutton)

        // Set up SharedPreferences
        val sharedPreferences: SharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)

        saveButton.setOnClickListener {
            val height = etHeight.text.toString()
            val weight = etWeight.text.toString()
            val selectedGenderId = radioGroupGender.checkedRadioButtonId
            val gender = if (selectedGenderId != -1) {
                findViewById<RadioButton>(selectedGenderId).text.toString()
            } else {
                ""
            }

            if (height.isNotEmpty() && weight.isNotEmpty() && gender.isNotEmpty()) {
                saveToSharedPreferences(sharedPreferences, height, weight, gender)
                Toast.makeText(this, "Data saved successfully!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please fill all fields.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveToSharedPreferences(sharedPreferences: SharedPreferences, height: String, weight: String, gender: String) {
        val editor = sharedPreferences.edit()
        editor.putString("Height", height)
        editor.putString("Weight", weight)
        editor.putString("Gender", gender)
        editor.apply() // Save changes
    }
}
