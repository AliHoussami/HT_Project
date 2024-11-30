package com.example.ht
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.ht.databinding.ActivityMainBinding
import android.content.Intent


class Originalpagemain : ComponentActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Set up button listeners for various features
        binding.btnBmiCalculator.setOnClickListener {
            startActivity(Intent(this,Bmi_Activity::class.java))
        }
        binding.btnBmrCalculator.setOnClickListener {
            startActivity(Intent(this,Bmr_Calculator_Activity::class.java))
        }
        binding.btnBodyFatCalculator.setOnClickListener {
            startActivity(Intent(this,BodyFatCalculatorActivity::class.java))
        }
        binding.btnInfo.setOnClickListener {
            startActivity(Intent(this,height_weight::class.java))
        }
        binding.btnHealthSummary.setOnClickListener {
            startActivity(Intent(this,Summary_Activity::class.java))
        }
        binding.btnHelpFaq.setOnClickListener {
            startActivity(Intent(this, Help_Activity::class.java))
        }
        binding.btnAbout.setOnClickListener {
            startActivity(Intent(this, About_Activity::class.java))
        }
        binding.btnGoToLoginSignup.setOnClickListener {
            startActivity(Intent(this, LoginSignupActivity::class.java))
            finish()
        }
        binding.buttonOpenFoodActivity.setOnClickListener {
            startActivity(Intent(this, FoodHostActivity::class.java))
        }
    }
    private var allowBack = false
    override fun onBackPressed() {
        if (allowBack) {
            super.onBackPressed() // Allow back navigation only if the flag is true
        }
    }
}








