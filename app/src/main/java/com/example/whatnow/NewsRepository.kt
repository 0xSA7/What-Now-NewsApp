package com.example.whatnow

import com.example.whatnow.api.NewsCallable
import com.example.whatnow.api.RetrofitFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewsRepository(private val retrofitFactory: RetrofitFactory) {

    private val newsCallable: NewsCallable

    init {
        val retrofit = retrofitFactory.createRetrofit()
        newsCallable = retrofit.create(NewsCallable::class.java)
    }

    fun getNews(queryUrl: String) = newsCallable.getNews(queryUrl)
}