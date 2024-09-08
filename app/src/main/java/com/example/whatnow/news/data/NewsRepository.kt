package com.example.whatnow.news.data

import com.example.whatnow.core.api.RetrofitFactory

class NewsRepository(private val retrofitFactory: RetrofitFactory) {

    private val newsCallable: NewsCallable

    init {
        val retrofit = retrofitFactory.createRetrofit()
        newsCallable = retrofit.create(NewsCallable::class.java)
    }

    fun getNews(queryUrl: String) = newsCallable.getNews(queryUrl)
}