package com.example.newsapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarkedArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmarkArticle(bookmarkedArticle: BookmarkedArticle)

    @Query("SELECT * FROM bookmarked_articles")
    fun getAllBookmarkedArticles(): Flow<List<BookmarkedArticle>>

    @Query("DELETE FROM bookmarked_articles WHERE url = :articleUrl")
    suspend fun deleteBookmarkedArticleByUrl(articleUrl: String)
}