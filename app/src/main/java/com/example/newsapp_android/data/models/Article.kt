package com.example.newsapp_android.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity( tableName = "articles")
data class Article(
    @PrimaryKey(autoGenerate = true)
    var id: Int?= 0, //it causes problem when the id is null when serializing it so we set it to 0 by default
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: Source?,
    val title: String?,
    val url: String?,
    val urlToImage: String?
) : Serializable
