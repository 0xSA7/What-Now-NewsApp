package com.example.whatnow.core.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.whatnow.R
import com.example.whatnow.auth.SignInActivity

class SplashActivity : AppCompatActivity() {

    private val SPLASH_DISPLAY_LENGTH: Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val enterAnimation = AnimationUtils.loadAnimation(this, R.anim.img_enter_animation)
        val exitAnimation = AnimationUtils.loadAnimation(this, R.anim.exit_animation)
        val rootLayout = findViewById<View>(R.id.splash)

        val imageView = findViewById<ImageView>(R.id.splashImage)
        imageView.startAnimation(enterAnimation)

        Handler(Looper.getMainLooper()).postDelayed({
            val sharedPreferences: SharedPreferences =
                getSharedPreferences("onboarding_prefs", MODE_PRIVATE)
            val isFirstTime: Boolean = sharedPreferences.getBoolean("isFirstTime", true)

            Log.d("SplashActivity", "isFirstTime: $isFirstTime")

            val intent = if (isFirstTime) {
                Log.d("SplashActivity", "Redirecting to Onboarding")
                Intent(this, Onboarding::class.java)
            } else {
                Log.d("SplashActivity", "Redirecting to SignupActivity")
                Intent(this, SignInActivity::class.java)
            }

            Log.d("SplashActivity", "Starting intent: ${intent.component?.className}")



            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(intent)
                overridePendingTransition(R.anim.img_enter_animation, R.anim.exit_animation)
                finish()
            }, exitAnimation.duration)

        }, SPLASH_DISPLAY_LENGTH)
    }
}
