package com.example.whatnow.api

enum class SortBy(val value: String) {
    RELEVANCY("relevancy"),
    POPULARITY("popularity"),
    PUBLISHED_AT("publishedAt"),
    None("");
}