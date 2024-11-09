package com.example.ht

import android.os.Bundle
import androidx.activity.ComponentActivity
import android.content.Intent
import android.widget.Button

class LoginSignupActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_signup)

        val buttonRedirect = findViewById<Button>(R.id.loginButton)
        buttonRedirect.setOnClickListener {
            // Intent to open NextActivity
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }

        val buttonRedirectt = findViewById<Button>(R.id.signupButton)
        buttonRedirectt.setOnClickListener {
            // Intent to open NextActivity
            val intent = Intent(this,SignupActivity::class.java)
            startActivity(intent)
        }
    }
}