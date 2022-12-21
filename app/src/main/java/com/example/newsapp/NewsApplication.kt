package com.example.newsapp

import android.app.Application
import com.example.newsapp.db.ArticleDatabase

class NewsApplication: Application() {
    val database by lazy { ArticleDatabase.createDataBase(this) }
}