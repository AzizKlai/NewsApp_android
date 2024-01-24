package com.example.newsapp_android.db

import androidx.room.TypeConverter
import com.example.newsapp_android.models.Source

class Converters {
    //Convert a Source to String
    @TypeConverter
    fun fromSource(source: Source)=source.name
    //Convert a string to Source
    @TypeConverter
    fun toSource(name: String)=Source(name, name)
}