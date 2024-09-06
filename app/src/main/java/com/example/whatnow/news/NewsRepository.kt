package com.example.whatnow.news

import com.example.whatnow.api.NewsCallable
import com.example.whatnow.api.RetrofitFactory

class NewsRepository(private val retrofitFactory: RetrofitFactory) {

    private val newsCallable: NewsCallable

    init {
        val retrofit = retrofitFactory.createRetrofit()
        newsCallable = retrofit.create(NewsCallable::class.java)
    }

    fun getNews(queryUrl: String) = newsCallable.getNews(queryUrl)
}