package com.example.newsapp.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.newsapp.db.ArticleDao
import com.example.newsapp.models.Article
import com.example.newsapp.network.BreakingNewsPagingSource
import com.example.newsapp.network.NewsApi
import com.example.newsapp.network.SearchNewsPagingSource
import com.example.newsapp.util.Constants.Companion.QUERY_PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val dao: ArticleDao,
    private val newsApi: NewsApi
) {
    fun getBreakingNews(): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(
                pageSize = QUERY_PAGE_SIZE,
                initialLoadSize = QUERY_PAGE_SIZE,
                prefetchDistance = 1,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { BreakingNewsPagingSource(newsApi) }
        ).flow
    }

    fun getSearchNews(query: String): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(
                pageSize = QUERY_PAGE_SIZE,
                initialLoadSize = QUERY_PAGE_SIZE,
                prefetchDistance = 1,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { SearchNewsPagingSource(newsApi, query) }
        ).flow
    }

    suspend fun insert(article: Article) = dao.insert(article)

    fun getSavedNews() = dao.getAllArticles()

    suspend fun delete(article: Article) {
        dao.deleteArticle(article)
    }
}