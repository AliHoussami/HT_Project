package com.example.ht
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import android.widget.Button

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        val mybutton: Button = findViewById(R.id.btnGetStarted)
        mybutton.setOnClickListener {
            val intent = Intent(this, LoginSignupActivity::class.java)
            startActivity(intent)
        }
    }
}

