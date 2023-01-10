package com.example.newsapp.ui.breakingnews

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.NewsApplication
import com.example.newsapp.R
import com.example.newsapp.ui.NewsAdapter
import com.example.newsapp.models.Article
import com.example.newsapp.network.NewsApiService
import com.example.newsapp.repository.NewsRepository
import com.example.newsapp.util.Constants.Companion.QUERY_PAGE_SIZE
import com.example.newsapp.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_breaking_news.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BreakingNewsFragment : Fragment(R.layout.fragment_breaking_news) {

    private val viewModel: BreakingNewsViewModel by activityViewModels {
        BreakingNewsViewModelProviderFactory(
            activity?.application as NewsApplication,
            NewsRepository(
                (activity?.application as NewsApplication).database.getArticleDao(),
                NewsApiService.apiService
            )
        )
    }
    private lateinit var newsAdapter: NewsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.breakingNews.collect { response ->
                    when (response) {
                        is Resource.Success -> {
                            hideProgressBar()
                            response.data?.let{ newsResponse ->
                                newsAdapter.submitList(newsResponse.articles.toList())
                                val totalPages = newsResponse.totalResults/ QUERY_PAGE_SIZE + 2
                                isLastPage = viewModel.breakingNewsPage == totalPages
                                if(isLastPage){
                                    rvBreakingNews.setPadding(0,0,0,0)
                                }
                            }
                        }

                        is Resource.Error -> {
                            hideProgressBar()
                            response.message?.let{ message ->
                                Toast.makeText(context,"An error occurred: $message",Toast.LENGTH_LONG).show()
                            }
                        }

                        is Resource.Loading -> {
                            showProgressBar()
                        }
                    }
                }
            }
        }
    }

    private fun hideProgressBar(){
        paginationProgressBar.visibility = View.INVISIBLE
        isLoading = false
    }

    private fun showProgressBar(){
        paginationProgressBar.visibility = View.VISIBLE
        isLoading = true
    }

    var isLoading = false
    var isLastPage = false
    var isScrolling = false

//    private val scrollListener = object: RecyclerView.OnScrollListener() {
//        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//            super.onScrolled(recyclerView, dx, dy)
//
//            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
//            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
//            val visibleItemCount = layoutManager.childCount
//            val totalItemCount = layoutManager.itemCount
//
//            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
//            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
//            val isNotAtBeginning = firstVisibleItemPosition >= 0
//            val isTotalMoreThanVisible = totalItemCount >= QUERY_PAGE_SIZE
//            val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning &&
//                    isTotalMoreThanVisible && isScrolling
//            if(shouldPaginate){
//                viewModel.getBreakingNews("in")
//                isScrolling = false
//            }
//        }
//
//        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//            super.onScrollStateChanged(recyclerView, newState)
//            if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
//                isScrolling = true
//            }
//        }
//    }

    private fun setupRecyclerView(){
        newsAdapter = NewsAdapter { article ->
            onClick(article)
        }
        rvBreakingNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
//            addOnScrollListener(this@BreakingNewsFragment.scrollListener)
        }
    }

    private fun onClick(article: Article){
//        val bundle = Bundle().apply {
//            putSerializable("article", article)
//        }
//        findNavController().navigate(
//            R.id.action_breakingNewsFragment_to_articleFragment,
//            bundle
//        )
    }
}