package com.example.ht

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
            val username = binding.usernameEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            registerUser(username, password)
        }
    }
    private fun registerUser(username: String, password: String) {
        val user = User(username = username, password = password)
        Log.d("SignupActivity", "Username: $username, Password: $password")
        GlobalScope.launch(Dispatchers.IO) {
            val response = RetrofitInstance.api.registerUser(user)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    Log.d("SignupActivity", "Registration successful")
                    Toast.makeText(this@SignupActivity, "Registration successful", Toast.LENGTH_SHORT).show()
                    finish() // Go back to the previous screen
                } else {
                    Log.e("SignupActivity", "Registration failed with code: ${response.code()}")
                    Toast.makeText(this@SignupActivity, "Registration failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
