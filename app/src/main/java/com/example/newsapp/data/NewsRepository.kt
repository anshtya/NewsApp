package com.example.newsapp.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.newsapp.data.network.breakingnews.dao.ArticleDao
import com.example.newsapp.data.local.ArticleDatabase
import com.example.newsapp.data.local.BookmarkedArticle
import com.example.newsapp.data.local.BookmarkedArticleDao
import com.example.newsapp.data.network.model.Article
import com.example.newsapp.data.network.api.NewsApi
import com.example.newsapp.data.network.searchNews.SearchNewsPagingSource
import com.example.newsapp.data.network.breakingnews.BreakingNewsRemoteMediator
import com.example.newsapp.data.network.breakingnews.dao.RemoteKeyDao
import com.example.newsapp.util.Constants.Companion.QUERY_PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val db: ArticleDatabase,
    private val articleDao: ArticleDao,
    private val remoteKeyDao: RemoteKeyDao,
    private val bookmarkedArticleDao: BookmarkedArticleDao,
    private val newsApi: NewsApi
) {
    @OptIn(ExperimentalPagingApi::class)
    fun getBreakingNews(): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(
                pageSize = QUERY_PAGE_SIZE,
                initialLoadSize = QUERY_PAGE_SIZE,
                prefetchDistance = 1,
                enablePlaceholders = false
            ),
            remoteMediator = BreakingNewsRemoteMediator(newsApi, db, articleDao, remoteKeyDao),
            pagingSourceFactory = { articleDao.getAllArticles() }
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

    val savedNews = bookmarkedArticleDao.getAllBookmarkedArticles()

    suspend fun insertBookmarkArticle(bookmarkedArticle: BookmarkedArticle) =
        bookmarkedArticleDao.insertBookmarkArticle(bookmarkedArticle)

    suspend fun deleteBookmarkedArticle(articleUrl: String) =
        bookmarkedArticleDao.deleteBookmarkedArticleByUrl(articleUrl)
}