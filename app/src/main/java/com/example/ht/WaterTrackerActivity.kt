package com.example.ht

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.ht.databinding.ActivityWaterTrackerBinding

class WaterTrackerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWaterTrackerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWaterTrackerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Load the MainFragment by default
        loadFragment(MainFragment())

        // Set up button click listeners
        binding.showMainFragmentButton.setOnClickListener {
            loadFragment(MainFragment())
        }

        binding.showSettingsFragmentButton.setOnClickListener {
            loadFragment(SettingsFragment())
        }
    }
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, fragment)
            .commit()
    }
}