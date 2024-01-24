package com.example.newsapp_android.repository

import com.example.newsapp_android.api.RetrofitHelper
import com.example.newsapp_android.db.ArticleDatabase
import com.example.newsapp_android.models.Article

class NewsRepository(private val db: ArticleDatabase)
{
    suspend fun getHeadlineNews(countryCode: String, pageNumber: Int) =
        RetrofitHelper.api.getHeadlineNews(countryCode, pageNumber)

    suspend fun searchNews(searchQuery: String, pageNumber: Int) =
        RetrofitHelper.api.searchNews(searchQuery, pageNumber)

    fun getSavedNews() = db.getArticleDao().getAllArticles()

    suspend fun upsert(article: Article) = db.getArticleDao().upsert(article)

    suspend fun deleteArticle(article: Article) = db.getArticleDao().deleteArticle(article)
}
