package com.example.ht
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.ht.data.model.User
import com.example.ht.data.network.RetrofitInstance
import com.example.ht.databinding.ActivitySignUpBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.util.Log

class SignupActivity : ComponentActivity() {

    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signupButton.setOnClickListener {
            val firstName = binding.firstNameEditText.text.toString()
            val lastName = binding.lastNameEditText.text.toString()
            val phoneNumber = binding.phoneNumberEditText.text.toString()
            val username = binding.usernameEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (validateInput(firstName, lastName, phoneNumber, username, password)) {
                registerUser(firstName, lastName, phoneNumber, username, password)
            }
        }
    }

    private fun validateInput(
        firstName: String,
        lastName: String,
        phoneNumber: String,
        username: String,
        password: String
    ): Boolean {
        return when {
            firstName.isEmpty() -> {
                showToast("First Name cannot be empty")
                false
            }
            lastName.isEmpty() -> {
                showToast("Last Name cannot be empty")
                false
            }
            phoneNumber.isEmpty() -> {
                showToast("Phone Number cannot be empty")
                false
            }
            username.isEmpty() -> {
                showToast("Username cannot be empty")
                false
            }
            password.isEmpty() -> {
                showToast("Password cannot be empty")
                false
            }
            else -> true
        }
    }

    private fun registerUser(
        firstName: String,
        lastName: String,
        phoneNumber: String,
        username: String,
        password: String
    ) {
        val user = User(
            firstName = firstName,
            lastName = lastName,
            phoneNumber = phoneNumber,
            username = username,
            password = password
        )
        Log.d("SignupActivity", "Registering User: $user")
        GlobalScope.launch(Dispatchers.IO) {
            val response = RetrofitInstance.api.registerUser(user)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    Log.d("SignupActivity", "Registration successful")
                    Toast.makeText(this@SignupActivity, "Registration successful", Toast.LENGTH_SHORT).show()
                    // Navigate to Info_Activity after signup
                    startActivity(Intent(this@SignupActivity, Info_Activity::class.java))
                    finish() // Close this activity to prevent going back
                } else {
                    Log.e("SignupActivity", "Registration failed with code: ${response.code()}")
                    Toast.makeText(this@SignupActivity, "Registration failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
