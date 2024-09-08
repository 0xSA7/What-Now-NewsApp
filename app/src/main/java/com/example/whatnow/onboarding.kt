package com.example.whatnow

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.ViewFlipper
import androidx.appcompat.app.AppCompatActivity

class Onboarding : AppCompatActivity() {

    private lateinit var viewFlipper: ViewFlipper
    private lateinit var nextButton: Button
    private lateinit var backButton: Button
    private lateinit var skipButton: Button
    private lateinit var textSlide: TextView

    private val slideTexts = arrayOf(
        "Welcome to our app",
        "Discover amazing features",
        "Get started now"
    )
    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_slider)

        viewFlipper = findViewById(R.id.viewFlipper)
        nextButton = findViewById(R.id.nextbtn)
        backButton = findViewById(R.id.backbtn)
        skipButton = findViewById(R.id.skipButton)
        textSlide = findViewById(R.id.textSlide)

        updateSlideText()

        viewFlipper.setOutAnimation(this, android.R.anim.slide_out_right)
        viewFlipper.setInAnimation(this, android.R.anim.slide_in_left)

        nextButton.setOnClickListener {
            if (currentIndex < viewFlipper.childCount - 1) {
                currentIndex++
                viewFlipper.showNext()
                updateSlideText()
            } else {
                navigateToSignup()
            }
        }

        backButton.setOnClickListener {
            if (currentIndex > 0) {
                currentIndex--
                viewFlipper.showPrevious()
                updateSlideText()
            }
        }

        skipButton.setOnClickListener {
            skipOnboarding()
        }
    }

    private fun updateSlideText() {
        textSlide.text = slideTexts[currentIndex]
    }

    private fun navigateToSignup() {
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("onboarding_prefs", MODE_PRIVATE)
        sharedPreferences.edit().putBoolean("isFirstTime", false).apply()

        startActivity(Intent(this, SignupActivity::class.java))
        finish()
    }

    private fun skipOnboarding() {
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("onboarding_prefs", MODE_PRIVATE)
        sharedPreferences.edit().putBoolean("isFirstTime", false).apply()
        navigateToSignup()
    }
}
