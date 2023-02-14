package com.example.newsapp.ui.bookmarkednews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.local.BookmarkedArticle
import com.example.newsapp.data.network.model.Article
import com.example.newsapp.data.repositories.BookmarkedNewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkedNewsViewModel @Inject constructor(
    private val bookmarkedNewsRepository: BookmarkedNewsRepository
): ViewModel() {

    val bookmarkedNews = bookmarkedNewsRepository.bookmarkedNews.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )

    fun deleteBookmarkedArticle(articleUrl: String) = viewModelScope.launch {
        bookmarkedNewsRepository.deleteBookmarkedArticle(articleUrl)
    }

    fun insertBookmarkedArticle(bookmarkedArticle: BookmarkedArticle) = viewModelScope.launch {
        bookmarkedNewsRepository.insertBookmarkedArticle(bookmarkedArticle)
    }

    fun updateBookmarkStatus(article: Article, isBookmarked: Boolean) = viewModelScope.launch {
        bookmarkedNewsRepository.updateBookmarkedStatus(article, isBookmarked)
    }
}