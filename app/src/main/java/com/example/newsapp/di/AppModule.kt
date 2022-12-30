package com.example.newsapp.di

import android.content.Context
import androidx.room.Room
import com.example.newsapp.db.ArticleDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

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
    fun provideDao(db: ArticleDatabase) = db.getArticleDao()
}