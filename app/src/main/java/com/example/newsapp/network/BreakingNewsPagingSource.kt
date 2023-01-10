package com.example.newsapp.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.newsapp.models.Article
import com.example.newsapp.util.Constants
import retrofit2.HttpException
import java.io.IOException

class BreakingNewsPagingSource(
    private val service: NewsApiService,
    private val query: String
): PagingSource<Int, Article>() {
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val position = params.key ?: Constants.BREAKING_NEWS_PAGE
//        val apiQuery = query
        return try {
            val response = NewsApiService.apiService.getBreakingNews(Constants.COUNTRY_CODE, position)
            val news = response.articles
            val nextKey = if (news.isEmpty()) {
                null
            } else {
                // initial load size = 3 * NETWORK_PAGE_SIZE
                // ensure we're not requesting duplicating items, at the 2nd request
                position + (params.loadSize / Constants.QUERY_PAGE_SIZE)
            }
            LoadResult.Page(
                data = news,
                prevKey = if (position == Constants.BREAKING_NEWS_PAGE) null else position - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

}