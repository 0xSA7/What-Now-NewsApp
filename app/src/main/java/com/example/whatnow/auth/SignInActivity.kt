//package com.example.whatnow.auth
//
//import android.content.Context
//import android.content.Intent
//import android.content.SharedPreferences
//import android.os.Bundle
//import android.widget.Toast
//import androidx.activity.enableEdgeToEdge
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.view.ViewCompat
//import androidx.core.view.WindowInsetsCompat
//import com.example.whatnow.R
//import com.example.whatnow.setteings.data.SettingAPI
//import com.example.whatnow.databinding.ActivitySignInBinding
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.auth.ktx.auth
//import com.google.firebase.ktx.Firebase
//
//class SignInActivity : AppCompatActivity() {
//    private lateinit var binding: ActivitySignInBinding
//    private lateinit var auth: FirebaseAuth
//    private lateinit var sharedPreferences: SharedPreferences
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        binding = ActivitySignInBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
//
//        // Initialize Firebase Auth
//        auth = Firebase.auth
//        // Initialize sharedPreferences
//        sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
//
//        loaduserdata()
//
//        // Sign-up redirection
//        binding.notUserTv.setOnClickListener {
//            startActivity(Intent(this, SignupActivity::class.java))
//            finish()
//        }
//
//        // Login Button Logic
//        binding.loginBtn.setOnClickListener {
//            val email = binding.emailEt.text.toString()
//            val password = binding.passwordEt.text.toString()
//            if (email.isBlank() || password.isBlank()) {
//                Toast.makeText(this, "Missing Field/s!", Toast.LENGTH_SHORT).show()
//            } else {
//                // binding.progress.isVisible = true
//                login(email, password)
//            }
//        }
//
//        // Forgot Password Logic
//        binding.forgotPassTv.setOnClickListener {
//            val email = binding.emailEt.text.toString()
//            if (email.isBlank()) {
//                binding.emailEt.error = "Required!"
//            } else {
//                sendPasswordResetEmail(email)
//            }
//        }
//    }
//
//    private fun sendPasswordResetEmail(email: String) {
//        auth.sendPasswordResetEmail(email)
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    Toast.makeText(this, "Password reset email sent!", Toast.LENGTH_SHORT).show()
//                } else {
//                    val errorMessage = task.exception?.message ?: "Error occurred!"
//                    Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
//                }
//            }
//    }
//
//    private fun login(email: String, password: String) {
//        auth.signInWithEmailAndPassword(email, password)
//            .addOnCompleteListener(this) { task ->
//                if (task.isSuccessful) {
//                    // binding.progress.isVisible = false
//                    if (auth.currentUser!!.isEmailVerified) {
//                        saveuserdata(email, password)
//                        startActivity(Intent(this, SettingAPI::class.java))
//                        finish()
//                    } else {
//                        Toast.makeText(this, "Please verify your email first", Toast.LENGTH_SHORT)
//                            .show()
//                    }
//                } else {
//                    val message = task.exception!!.message
//                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
//                    // binding.progress.isVisible = false
//                }
//            }
//    }
//
//    private fun saveuserdata(email: String, password: String) {
//        val editor = sharedPreferences.edit()
//        editor.putString("email", email)
//        editor.putString("password", password)
//        editor.apply()
//
//    }
//
//
//    private fun loaduserdata() {
//
//        val savedEmail = sharedPreferences.getString("email", "")
//        val savedPassword = sharedPreferences.getString("password", "")
//
//        if (!savedEmail!!.isBlank() && !savedPassword!!.isBlank()) {
//
//            binding.emailEt.setText(savedEmail)
//            binding.passwordEt.setText(savedPassword)
//        }
//
//
//    }
//
//
//    private fun checkEmailVerification() {
//        // This function can be used if needed to handle email verification logic
//    }
//}

package com.example.whatnow.auth

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.whatnow.R
import com.example.whatnow.setteings.data.SettingAPI
import com.example.whatnow.databinding.ActivitySignInBinding

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val viewModel: AuthViewModel by viewModels { AuthViewModelFactory(UserRepository()) }

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

        // Initialize sharedPreferences
        sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)

        loadUserData()

        binding.notUserTv.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
            finish()
        }

        binding.loginBtn.setOnClickListener {
            val email = binding.emailEt.text.toString()
            val password = binding.passwordEt.text.toString()
            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Missing Field/s!", Toast.LENGTH_SHORT).show()
            } else {
                // binding.progress.isVisible = true
                viewModel.signIn(email, password)
            }
        }

        binding.forgotPassTv.setOnClickListener {
            val email = binding.emailEt.text.toString()
            if (email.isBlank()) {
                binding.emailEt.error = "Required!"
            } else {
                viewModel.sendPasswordResetEmail(email)
            }
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.signInResult.observe(this) { result ->
            result.onSuccess {
                saveUserData(binding.emailEt.text.toString(), binding.passwordEt.text.toString())
                startActivity(Intent(this, SettingAPI::class.java))
                finish()
            }.onFailure { exception ->
                Toast.makeText(this, exception.message, Toast.LENGTH_SHORT).show()
                // binding.progress.isVisible = false
            }
        }

        viewModel.passwordResetResult.observe(this) { result ->
            result.onSuccess {
                Toast.makeText(this, "Password reset email sent!", Toast.LENGTH_SHORT).show()
            }.onFailure { exception ->
                Toast.makeText(this, exception.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveUserData(email: String, password: String) {
        val editor = sharedPreferences.edit()
        editor.putString("email", email)
        editor.putString("password", password)
        editor.apply()
    }

    private fun loadUserData() {
        val savedEmail = sharedPreferences.getString("email", "")
        val savedPassword = sharedPreferences.getString("password", "")

        if (!savedEmail.isNullOrBlank() && !savedPassword.isNullOrBlank()) {
            binding.emailEt.setText(savedEmail)
            binding.passwordEt.setText(savedPassword)
        }
    }
}




