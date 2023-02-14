package com.example.newsapp.di

import android.content.Context
import androidx.room.Room
import com.example.newsapp.data.db.ArticleDatabase
import com.example.newsapp.data.local.BookmarkedNewsDao
import com.example.newsapp.data.network.breakingnews.dao.ArticleDao
import com.example.newsapp.data.network.breakingnews.dao.RemoteKeyDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext app: Context) =
        Room.databaseBuilder(
            app,
            ArticleDatabase::class.java,
            "article_db.db"
        ).fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideArticleDao(db: ArticleDatabase): ArticleDao = db.getArticleDao()

    @Provides
    @Singleton
    fun provideRemoteKeyDao(db: ArticleDatabase): RemoteKeyDao = db.getRemoteKeyDao()

    @Provides
    @Singleton
    fun provideBookmarkedNewsDao(db: ArticleDatabase): BookmarkedNewsDao = db.getBookmarkedNewsDao()
}