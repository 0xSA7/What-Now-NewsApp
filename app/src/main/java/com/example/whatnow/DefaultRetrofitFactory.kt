// File: app/src/main/java/com/example/whatnow/api/DefaultRetrofitFactory.kt
package com.example.whatnow

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DefaultRetrofitFactory : RetrofitFactory {
    override fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}