package com.example.newsapp.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.newsapp.data.local.ArticleDao
import com.example.newsapp.data.network.model.Article
import com.example.newsapp.data.network.BreakingNewsPagingSource
import com.example.newsapp.data.network.api.NewsApi
import com.example.newsapp.data.network.SearchNewsPagingSource
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