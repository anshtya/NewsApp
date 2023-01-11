package com.example.newsapp.ui.searchnews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.newsapp.databinding.FragmentSearchNewsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchNewsFragment : Fragment() {

    private lateinit var binding: FragmentSearchNewsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

//    private val viewModel: NewsViewModel by activityViewModels()
//    private lateinit var newsAdapter: NewsAdapter
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        setupRecyclerView()
//
//        var job: Job? = null
//        etSearch.addTextChangedListener { editable ->
//            job?.cancel()
//            job = MainScope().launch {
//                delay(SEARCH_NEWS_TIME_DELAY)
//                if(editable.toString().isNotEmpty()){
//                    viewModel.searchNews(editable.toString())
//                }
//            }
//        }
//
//        viewLifecycleOwner.lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                viewModel.searchNews.collect { response ->
//                    when (response) {
//                        is Resource.Success -> {
//                            hideProgressBar()
//                            response.data?.let{ newsResponse ->
//                                newsAdapter.submitList(newsResponse.articles.toList())
//                                val totalPages = newsResponse.totalResults/ Constants.QUERY_PAGE_SIZE + 2
//                                isLastPage = viewModel.searchNewsPage == totalPages
//                                if(isLastPage){
//                                    rvSearchNews.setPadding(0,0,0,0)
//                                }
//                            }
//                        }
//
//                        is Resource.Error -> {
//                            hideProgressBar()
//                            response.message?.let{ message ->
//                                Toast.makeText(context,"An error occurred: $message", Toast.LENGTH_LONG).show()
//                            }
//                        }
//
//                        is Resource.Loading -> {
//                            showProgressBar()
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    private fun hideProgressBar(){
//        paginationProgressBar.visibility = View.INVISIBLE
//        isLoading = false
//    }
//
//    private fun showProgressBar(){
//        paginationProgressBar.visibility = View.VISIBLE
//        isLoading = true
//    }
//
//    var isLoading = false
//    var isLastPage = false
//    var isScrolling = false
//
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
//            val isTotalMoreThanVisible = totalItemCount >= Constants.QUERY_PAGE_SIZE
//            val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning &&
//                    isTotalMoreThanVisible && isScrolling
//            if (shouldPaginate) {
//                viewModel.searchNews(etSearch.text.toString())
//                isScrolling = false
//            }
//        }
//
//        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//            super.onScrollStateChanged(recyclerView, newState)
//            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
//                isScrolling = true
//            }
//        }
//    }
//
//    private fun setupRecyclerView(){
//        newsAdapter = NewsAdapter { article ->
//            onClick(article)
//        }
//        rvSearchNews.apply {
//            adapter = newsAdapter
//            layoutManager = LinearLayoutManager(activity)
//            addOnScrollListener(this@SearchNewsFragment.scrollListener)
//        }
//    }
//
//    private fun onClick(article: Article){
//        val bundle = Bundle().apply {
//            putSerializable("article", article)
//        }
//        findNavController().navigate(
//            R.id.action_searchNewsFragment_to_articleFragment,
//            bundle
//        )
//    }
}