package com.example.whatnow.news.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface NewsCallable {
    @GET
    fun getNews(@Url url: String): Call<News>
}