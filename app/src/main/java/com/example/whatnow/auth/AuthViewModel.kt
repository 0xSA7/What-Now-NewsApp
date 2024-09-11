package com.example.whatnow.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class AuthViewModel(private val repository: UserRepository) : ViewModel() {

    private val _signUpResult = MutableLiveData<Result<Boolean>>()
    val signUpResult: LiveData<Result<Boolean>> get() = _signUpResult

    private val _passwordResetResult = MutableLiveData<Result<Boolean>>()
    val passwordResetResult: LiveData<Result<Boolean>> get() = _passwordResetResult

    private val _signInResult = MutableLiveData<Result<Boolean>>()
    val signInResult: LiveData<Result<Boolean>> get() = _signInResult

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun signUp(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    verifyEmail()
                } else {
                    _signUpResult.value = Result.failure(task.exception ?: Exception("Sign up failed"))
                }
            }
    }

    private fun verifyEmail() {
        auth.currentUser?.sendEmailVerification()
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _signUpResult.value = Result.success(true)
                } else {
                    _signUpResult.value = Result.failure(task.exception ?: Exception("Failed to send verification email"))
                }
            }
    }

    fun sendPasswordResetEmail(email: String) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _passwordResetResult.value = Result.success(true)
                } else {
                    _passwordResetResult.value = Result.failure(task.exception ?: Exception("Failed to send password reset email"))
                }
            }
    }

    fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    if (auth.currentUser!!.isEmailVerified) {
                        _signInResult.value = Result.success(true)
                    } else {
                        _signInResult.value = Result.failure(Exception("Please verify your email first"))
                    }
                } else {
                    _signInResult.value = Result.failure(task.exception ?: Exception("Sign in failed"))
                }
            }
    }
}
