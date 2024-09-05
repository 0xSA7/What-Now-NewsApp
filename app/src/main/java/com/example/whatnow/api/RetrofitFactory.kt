// File: app/src/main/java/com/example/whatnow/api/RetrofitFactory.kt
package com.example.whatnow.api

import retrofit2.Retrofit

interface RetrofitFactory {
    fun createRetrofit(): Retrofit
}