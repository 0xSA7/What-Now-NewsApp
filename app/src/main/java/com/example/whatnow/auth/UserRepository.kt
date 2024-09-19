package com.example.whatnow.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class UserRepository {
    private val auth: FirebaseAuth = Firebase.auth

    fun login(email: String, password: String, callback: (Result<Boolean>) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(Result.success(auth.currentUser!!.isEmailVerified))
                } else {
                    callback(Result.failure(task.exception ?: Exception("Login failed")))
                }
            }
    }

    fun signUp(email: String, password: String, callback: (Result<Boolean>) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    sendVerificationEmail { callback(it) }
                } else {
                    callback(Result.failure(task.exception ?: Exception("Sign up failed")))
                }
            }
    }

    fun sendPasswordResetEmail(email: String, callback: (Result<Boolean>) -> Unit) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(Result.success(true))
                } else {
                    callback(Result.failure(task.exception ?: Exception("Failed to send reset email")))
                }
            }
    }

    private fun sendVerificationEmail(callback: (Result<Boolean>) -> Unit) {
        auth.currentUser?.sendEmailVerification()
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(Result.success(true))
                } else {
                    callback(Result.failure(task.exception ?: Exception("Failed to send verification email")))
                }
            }
    }
}
