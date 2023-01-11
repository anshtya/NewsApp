package com.example.newsapp.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.newsapp.models.Article

class BreakingNewsPagingSource(
    private val newsApi: NewsApi,
    private val countryCode: String
): PagingSource<Int, Article>() {

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val position = params.key ?: 1
//        val apiQuery = query
        return try {
            val response = newsApi.getBreakingNews(countryCode, position)
            LoadResult.Page(
                data = response.articles,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (position == response.totalResults) null else position + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}