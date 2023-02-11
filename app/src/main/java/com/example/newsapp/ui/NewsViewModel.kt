package com.example.newsapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.newsapp.data.network.model.Article
import com.example.newsapp.data.NewsRepository
import com.example.newsapp.data.local.BookmarkedArticle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {

    private val _searchNews = MutableStateFlow<PagingData<Article>>(PagingData.empty())
    val searchNews = _searchNews.asStateFlow()

    val searchNewsQuery = MutableStateFlow("")

    val breakingNews = newsRepository.getBreakingNews().cachedIn(viewModelScope)

    val savedNews = newsRepository.savedNews
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = listOf()
        )

    fun updateSearchQuery(newQuery: String) {
        searchNewsQuery.value = newQuery
    }

    fun getSearchNews(query: String) = viewModelScope.launch {
        newsRepository.getSearchNews(query)
            .cachedIn(viewModelScope)
            .collect { articles ->
                _searchNews.value = articles
            }
    }

    fun insertBookmarkArticle(bookmarkedArticle: BookmarkedArticle) {
        viewModelScope.launch {
            newsRepository.insertBookmarkArticle(bookmarkedArticle)
        }
    }

    fun deleteArticle(articleUrl: String) {
        viewModelScope.launch {
            newsRepository.deleteBookmarkedArticle(articleUrl)
        }
    }

    fun saveArticle(article: Article) {
        viewModelScope.launch {
            newsRepository.insertBookmarkArticle(
                BookmarkedArticle(
                    url = article.url,
                    author = article.author,
                    content = article.content,
                    publishedAt = article.publishedAt,
                    source = article.source,
                    title = article.title,
                    urlToImage = article.urlToImage
                )
            )
        }
    }
}