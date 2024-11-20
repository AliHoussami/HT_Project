package com.example.ht

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.activity.ComponentActivity

class Info_Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_info)

        // Initialize views
        val goalSpinner: Spinner = findViewById(R.id.goalSpinner)
        val ageEditText: EditText = findViewById(R.id.ageEditText)
        val genderRadioGroup: RadioGroup = findViewById(R.id.genderRadioGroup)
        val submitButton: Button = findViewById(R.id.submitButton)

        // Set up Spinner with goals
        ArrayAdapter.createFromResource(
            this,
            R.array.goals,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            goalSpinner.adapter = adapter
        }

        // Handle Submit button click
        submitButton.setOnClickListener {
            val age = ageEditText.text.toString()
            val selectedGoal = goalSpinner.selectedItem.toString()
            val selectedGenderId = genderRadioGroup.checkedRadioButtonId

            // Validate inputs
            if (age.isEmpty()) {
                Toast.makeText(this, "Please enter your age", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (selectedGenderId == -1) {
                Toast.makeText(this, "Please select your gender", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val selectedGender = findViewById<RadioButton>(selectedGenderId).text.toString()

            // Optionally display collected data
            Toast.makeText(
                this,
                "Age: $age, Gender: $selectedGender, Goal: $selectedGoal",
                Toast.LENGTH_SHORT
            ).show()

            // Navigate to MainActivity or another screen
            startActivity(Intent(this, Originalpagemain::class.java))
            finish() // Close this activity to prevent going back to it
        }
    }
}
