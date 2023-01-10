package com.example.newsapp.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.newsapp.db.ArticleDao
import com.example.newsapp.models.Article
import com.example.newsapp.network.BreakingNewsPagingSource
import com.example.newsapp.network.NewsApiService
import com.example.newsapp.util.Constants.Companion.QUERY_PAGE_SIZE
import kotlinx.coroutines.flow.Flow

class NewsRepository (
    private val dao: ArticleDao,
    private val service: NewsApiService
) {
    suspend fun getBreakingNews(countryCode: String): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(
                pageSize = QUERY_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { BreakingNewsPagingSource(service, countryCode) }
        ).flow
    }

    suspend fun searchNews(searchQuery: String, pageNumber: Int) =
        NewsApiService.apiService.searchForNews(searchQuery, pageNumber)

    suspend fun insert(article: Article) = dao.insert(article)

    fun getSavedNews() = dao.getAllArticles()

    suspend fun delete(article: Article) {
        dao.deleteArticle(article)
    }
}