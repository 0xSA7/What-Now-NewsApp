package com.example.whatnow

import retrofit2.Call
import retrofit2.http.GET

interface NewsCallable {
    @GET(BuildConfig.API_END_POINT.toString())
    fun getNews(): Call<News>
}