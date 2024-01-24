package com.example.newsapp_android.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.*
import com.example.newsapp_android.models.Article

@Dao
interface ArticleDao {
    @Query("SELECT* FROM articles")
    fun getAllArticles(): LiveData<List<Article>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(article: Article): Long

    @Delete
    suspend fun deleteArticle(article: Article)
}
