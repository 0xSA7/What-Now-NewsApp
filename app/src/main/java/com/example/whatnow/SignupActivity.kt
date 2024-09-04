package com.example.whatnow

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.example.whatnow.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Initialize View Binding
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Apply Window Insets
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize FirebaseAuth
        auth = Firebase.auth

        binding.alreadyUserTv.setOnClickListener {
             startActivity(Intent(this, SignInActivity::class.java))
             finish()
        }

        binding.signUpBtn.setOnClickListener {
            val email = binding.emailEt.text.toString()
            val pass = binding.passwordEt.text.toString()
            val conPass = binding.conPassEt.text.toString()

            when {
                email.isBlank() || pass.isBlank() || conPass.isBlank() ->
                    Toast.makeText(this, "Missing Field/s!", Toast.LENGTH_SHORT).show()
                pass.length < 6 ->
                    Toast.makeText(this, "Short Password!", Toast.LENGTH_SHORT).show()
                pass != conPass ->
                    Toast.makeText(this, "Passwords don't match", Toast.LENGTH_SHORT).show()
                else -> {
                   /// binding.progress.isVisible = true
                    signUpUser(email, pass)
                }
            }
        }
    }

    private fun signUpUser(email: String, pass: String) {
        auth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    verifyEmail()
                } else {
                    val message = task.exception?.message ?: "Sign up failed"
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                   // binding.progress.isVisible = false
                }
            }
    }

    private fun verifyEmail() {
        auth.currentUser?.sendEmailVerification()
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Verification Email Sent!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Failed to send verification email", Toast.LENGTH_SHORT).show()
                }
                //binding.progress.isVisible = false
            }
    }
}
