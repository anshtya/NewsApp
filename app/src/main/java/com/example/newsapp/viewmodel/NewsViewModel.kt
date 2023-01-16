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
): ViewModel() {

    private val _breakingNews = MutableStateFlow<PagingData<Article>>(PagingData.empty())
    val breakingNews: StateFlow<PagingData<Article>>
        get() = _breakingNews

    private val _searchNews = MutableStateFlow<PagingData<Article>>(PagingData.empty())
    val searchNews: StateFlow<PagingData<Article>>
        get() = _searchNews

    val savedNews: StateFlow<List<Article>> = newsRepository.getSavedNews()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = listOf()
        )

    init {
        getBreakingNews()
    }

    private fun getBreakingNews() = viewModelScope.launch {
        newsRepository.getBreakingNews()
            .cachedIn(viewModelScope)
            .collectLatest { articles ->
                _breakingNews.value = articles
            }
    }

    fun getSearchNews(query: String) = viewModelScope.launch {
        newsRepository.getSearchNews(query)
            .cachedIn(viewModelScope)
            .collectLatest { articles ->
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