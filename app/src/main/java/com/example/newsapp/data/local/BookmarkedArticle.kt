package com.example.newsapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.newsapp.data.network.model.Source
import java.io.Serializable

@Entity(tableName = "bookmarked_articles")
data class BookmarkedArticle(
    @PrimaryKey
    val url: String,
    val author: String?,
    val content: String?,
    val publishedAt: String?,
    val source: Source?,
    val title: String?,
    val urlToImage: String?
) : Serializable
