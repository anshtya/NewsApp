package com.example.newsapp.ui.bookmarkednews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.NewsRepository
import com.example.newsapp.data.local.BookmarkedArticle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkedNewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository
): ViewModel() {

    val bookmarkedNews = newsRepository.bookmarkedNews.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )

    fun deleteBookmarkedArticle(articleUrl: String) = viewModelScope.launch {
        newsRepository.deleteBookmarkedArticle(articleUrl)
    }

    fun insertBookmarkedArticle(bookmarkedArticle: BookmarkedArticle) = viewModelScope.launch {
        newsRepository.insertBookmarkedArticle(bookmarkedArticle)
    }

    fun updateBookmarkStatus(articleUrl: String, isBookmarked: Boolean) = viewModelScope.launch {
        newsRepository.updateBookmarkedStatus(articleUrl, isBookmarked)
    }
}