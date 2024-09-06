package com.example.whatnow.Repo

import com.example.whatnow.API.NewsCallable
import com.example.whatnow.API.RetrofitFactory

class NewsRepository(private val retrofitFactory: RetrofitFactory) {

    private val newsCallable: NewsCallable

    init {
        val retrofit = retrofitFactory.createRetrofit()
        newsCallable = retrofit.create(NewsCallable::class.java)
    }

    fun getNews(queryUrl: String) = newsCallable.getNews(queryUrl)
}