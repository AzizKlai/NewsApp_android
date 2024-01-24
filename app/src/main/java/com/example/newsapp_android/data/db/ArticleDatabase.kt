package com.example.newsapp_android.db

import android.content.Context
import androidx.room.*
import com.example.newsapp_android.models.Article

@Database(
    version = 1,
    entities = [Article::class]

)
@TypeConverters(Converters::class)
abstract class ArticleDatabase : RoomDatabase(){

    abstract fun getArticleDao(): ArticleDao

    companion object{
        @Volatile
        private var instance: ArticleDatabase? = null
        private val LOCK= Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ArticleDatabase::class.java,
                "newsArticleDB.db"
            ).build()
    }
}
