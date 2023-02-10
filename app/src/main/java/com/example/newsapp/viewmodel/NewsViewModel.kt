package com.example.newsapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.newsapp.data.network.model.Article
import com.example.newsapp.data.NewsRepository
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

    val savedNews: StateFlow<List<Article>> = newsRepository.savedNews
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

    fun deleteArticle(article: Article) {
        viewModelScope.launch {
            newsRepository.delete(article)
        }
    }

    fun saveArticle(article: Article) {
        viewModelScope.launch {
            newsRepository.insert(article)
        }
    }
}