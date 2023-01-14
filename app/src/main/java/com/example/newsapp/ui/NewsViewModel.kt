package com.example.newsapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.newsapp.models.Article
import com.example.newsapp.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository
): ViewModel() {

    init{
        getBreakingNews()
    }

    fun getBreakingNews() = newsRepository.getBreakingNews().cachedIn(viewModelScope)

    fun getSearchNews(query: String) = newsRepository.getSearchNews(query).cachedIn(viewModelScope)

    fun deleteArticle(article: Article) {
        viewModelScope.launch {
            newsRepository.delete(article)
        }
    }

    fun saveArticle(article: Article){
        viewModelScope.launch {
            newsRepository.insert(article)
        }
    }

    val savedNews: StateFlow<List<Article>> = newsRepository.getSavedNews()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = listOf()
        )
}