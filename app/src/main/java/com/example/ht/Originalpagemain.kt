package com.example.ht
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.ht.databinding.ActivityMainBinding
import android.content.Intent
import androidx.appcompat.app.AlertDialog
import androidx.activity.OnBackPressedCallback


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
            startActivity(Intent(this,Body_Fat_Calculator_Activity::class.java))
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
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Show a confirmation dialog before exiting
                AlertDialog.Builder(this@Originalpagemain)
                    .setTitle("Leave this Page?")
                    .setMessage("Are you sure you want to go back to the login/signup page?")
                    .setPositiveButton("Yes") { _, _ ->
                        // Redirect to LoginSignupActivity
                        startActivity(Intent(this@Originalpagemain, LoginSignupActivity::class.java))
                        finish() // Close OriginalPageMain
                    }
                    .setNegativeButton("No") { dialog, _ ->
                        // Dismiss the dialog, stay on the page
                        dialog.dismiss()
                    }
                    .show()
            }
        }
        onBackPressedDispatcher.addCallback(this, callback)
    }

}








