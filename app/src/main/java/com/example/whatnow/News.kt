package com.example.whatnow


data class News(
    val articles: ArrayList<Articles>
)
data class Articles(
    val title: String,
    val url: String,
    val urlToImage: String

)
