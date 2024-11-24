package com.example.ht

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit


class FoodHostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_host)

        // Dynamically load the FoodFragment into the container
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                replace(R.id.fragment_container, FoodFragment())
            }
        }
    }
}