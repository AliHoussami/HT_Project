package com.example.ht

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.ht.data.network.RetrofitInstance
import com.example.ht.databinding.ActivitySignInBinding // Updated to match the correct binding class
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : ComponentActivity() {

    private lateinit var binding: ActivitySignInBinding // Updated to ActivitySignInBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize binding
        binding = ActivitySignInBinding.inflate(layoutInflater) // Updated to ActivitySignInBinding
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("auth_token", null)

        // Redirect if already logged in
        if (token != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        binding.loginButton.setOnClickListener {
            val username = binding.usernameEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            loginUser(username, password)
        }
    }

    private fun loginUser(username: String, password: String) {
        GlobalScope.launch(Dispatchers.IO) {
            val response = RetrofitInstance.api.loginUser(username, password)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful && response.body()?.isNotEmpty() == true) {
                    // Save the logged-in username
                    val sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putString("LOGGED_IN_USER", username)
                    editor.apply()

                    // Navigate to Main Activity
                    Toast.makeText(this@LoginActivity, "Login successful", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@LoginActivity, Originalpagemain::class.java))
                    finish()
                } else {
                    Toast.makeText(this@LoginActivity, "Invalid username or password", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
