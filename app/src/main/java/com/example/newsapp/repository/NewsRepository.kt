package com.example.newsapp.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.newsapp.db.ArticleDao
import com.example.newsapp.models.Article
import com.example.newsapp.network.BreakingNewsPagingSource
import com.example.newsapp.network.NewsApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val dao: ArticleDao,
    private val newsApi: NewsApi
) {
    fun getBreakingNews(): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { BreakingNewsPagingSource(newsApi) }
        ).flow
    }

    suspend fun searchNews(searchQuery: String, pageNumber: Int) =
        newsApi.searchForNews(searchQuery, pageNumber)

    suspend fun insert(article: Article) = dao.insert(article)

    fun getSavedNews() = dao.getAllArticles()

    suspend fun delete(article: Article) {
        dao.deleteArticle(article)
    }
}