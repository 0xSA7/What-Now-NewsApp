package com.example.whatnow.core.data

enum class SortBy(val value: String) {
    RELEVANCY("relevancy"),
    POPULARITY("popularity"),
    PUBLISHED_AT("publishedAt"),
    None("");
}