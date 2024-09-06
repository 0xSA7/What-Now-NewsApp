package com.example.whatnow.API


data class News(
    val articles: ArrayList<Articles>,
    val totalResults: Int = articles.size
)

data class Articles(
    val title: String,
    val url: String,
    val urlToImage: String

)
