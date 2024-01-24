package com.example.newsapp_android.models

import com.example.newsapp_android.models.Article

data class NewsResponse(
    val articles: MutableList<Article>,
    val status: String,
    val totalResults: Int
)