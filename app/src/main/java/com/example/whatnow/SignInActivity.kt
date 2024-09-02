package com.example.whatnow

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.example.whatnow.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Initialize Firebase Auth
        auth = Firebase.auth

        binding.notUserTv.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
            finish()
        }


        binding.loginBtn.setOnClickListener {
            val email = binding.emailEt.text.toString()
            val password = binding.passwordEt.text.toString()
            if (email.isBlank() || password.isBlank())
                Toast.makeText(this, "Missing Field/s!", Toast.LENGTH_SHORT).show()
            else {
                binding.progress.isVisible = true
                login(email, password)
            }

        }

        binding.forgotPassTv.setOnClickListener {
        }

    }

    private fun login(email: String, password: String) {


        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    binding.progress.isVisible = false
                    if (auth.currentUser!!.isEmailVerified) {
                        val i = Intent(this , MainActivity::class.java)
                        startActivity(i)
                        finish()
                    } else {
                        Toast.makeText(this, "Please verify your email first", Toast.LENGTH_SHORT).show()

                    }
                } else {
                    val messege = task.exception!!.message
                    Toast.makeText(this , messege , Toast.LENGTH_SHORT).show()
                    binding.progress.isVisible = false

                }
            }


    }

    private fun sendPasswordResetEmail(email: String) {
        if (email.isBlank())
            binding.emailEt.error = "Required!"
        else {
        }
    }

}

private fun checkEmailVerification() {
}