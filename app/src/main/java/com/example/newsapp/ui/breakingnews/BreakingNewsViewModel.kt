package com.example.newsapp.ui.breakingnews

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.newsapp.NewsApplication
import com.example.newsapp.models.Article
import com.example.newsapp.models.NewsResponse
import com.example.newsapp.repository.NewsRepository
import com.example.newsapp.util.Resource
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class BreakingNewsViewModel(
    app: Application,
    private val newsRepository: NewsRepository
): AndroidViewModel(app) {

    lateinit var breakingNews: Flow<PagingData<Article>>
//    val savedNews: StateFlow<List<Article>> = newsRepository.getSavedNews()
//        .stateIn(
//            scope = viewModelScope,
//            started = SharingStarted.WhileSubscribed(5000),
//            initialValue = listOf()
//        )
//
//    private val _breakingNews = MutableStateFlow<Resource<NewsResponse>>(Resource.Loading())
//    val breakingNews: StateFlow<Resource<NewsResponse>>
//        get() = _breakingNews
//    var breakingNewsPage = 1
//    private var breakingNewsResponse: NewsResponse? = null
//
//    private val _searchNews = MutableStateFlow<Resource<NewsResponse>>(Resource.Loading())
//    val searchNews: StateFlow<Resource<NewsResponse>>
//        get() = _searchNews
//    var searchNewsPage = 1
//    private var searchNewsResponse: NewsResponse? = null
//    private var oldSearchQuery: String? = null
//    private var newSearchQuery: String? = null

    init {

        viewModelScope.launch {
            breakingNews = newsRepository.getBreakingNews("in").cachedIn(this)
        }

    }

//    fun getBreakingNews(countryCode: String) = viewModelScope.launch {
//        try {
//            if(hasInternetConnection()){
//                val response = newsRepository.getBreakingNews(countryCode, breakingNewsPage)
//                _breakingNews.value = handleBreakNewsResponse(response)
//            } else {
//                _breakingNews.value = Resource.Error("No Internet Connection")
//            }
//        } catch (t: Throwable){
//            when(t){
//                is IOException -> _breakingNews.value = Resource.Error("Network Failure")
//                else -> _breakingNews.value = Resource.Error("Conversion Error")
//            }
//        }
//    }

//    fun searchNews(searchQuery: String) = viewModelScope.launch {
//        newSearchQuery = searchQuery
//        if(newSearchQuery != oldSearchQuery) {
//            searchNewsPage = 1
//        }
//        try {
//            if(hasInternetConnection()){
//                val response = newsRepository.searchNews(searchQuery, searchNewsPage)
//                _searchNews.value = handleSearchNewsResponse(response)
//            } else {
//                _searchNews.value = Resource.Error("No Internet Connection")
//            }
//        } catch (t: Throwable){
//            when(t){
//                is IOException -> _searchNews.value = Resource.Error("Network Failure")
//                else -> _searchNews.value = Resource.Error("Conversion Error")
//            }
//        }
//    }

//    private fun handleBreakNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
//        if (response.isSuccessful) {
//            response.body()?.let { resultResponse ->
//                breakingNewsPage++
//                if(breakingNewsResponse == null){
//                    breakingNewsResponse = resultResponse
//                } else {
//                    val oldArticles = breakingNewsResponse?.articles
//                    val newArticles = resultResponse.articles
//                    oldArticles?.addAll(newArticles)
//                }
//                return Resource.Success(breakingNewsResponse ?: resultResponse)
//            }
//        }
//        return Resource.Error(response.message())
//    }
//
//    private fun handleSearchNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
//        if (response.isSuccessful) {
//            response.body()?.let { resultResponse ->
//                searchNewsPage++
//                if(searchNewsResponse == null || oldSearchQuery != newSearchQuery){
//                    oldSearchQuery = newSearchQuery
//                    searchNewsResponse  = resultResponse
//                } else {
//                    val oldArticles = searchNewsResponse?.articles
//                    val newArticles = resultResponse.articles
//                    oldArticles?.addAll(newArticles)
//                }
//                return Resource.Success(searchNewsResponse ?: resultResponse)
//            }
//        }
//        return Resource.Error(response.message())
//    }
//
//    fun saveArticle(article: Article) {
//        viewModelScope.launch {
//            newsRepository.insert(article)
//        }
//    }

    fun deleteArticle(article: Article) {
        viewModelScope.launch {
            newsRepository.delete(article)
        }
    }

    private fun hasInternetConnection(): Boolean{
        var result = false
        val connectivityManager = getApplication<NewsApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.run {
                connectivityManager.activeNetworkInfo?.run {
                    result = when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_ETHERNET -> true
                        else -> false
                    }

                }
            }
        }
        return result
    }
}

class BreakingNewsViewModelProviderFactory(
    private val app: Application,
    private val repository: NewsRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BreakingNewsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BreakingNewsViewModel(app, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}