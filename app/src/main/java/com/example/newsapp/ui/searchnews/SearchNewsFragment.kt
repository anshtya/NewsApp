package com.example.newsapp.ui.searchnews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.databinding.FragmentSearchNewsBinding
import com.example.newsapp.ui.NewsLoadStateAdapter
import com.example.newsapp.ui.NewsViewModel
import com.example.newsapp.ui.PagingNewsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchNewsFragment : Fragment() {

    private val viewModel: NewsViewModel by activityViewModels()
    private lateinit var binding: FragmentSearchNewsBinding
    private lateinit var pagingNewsAdapter: PagingNewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        binding.svSearch.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    viewLifecycleOwner.lifecycleScope.launch {
                        repeatOnLifecycle(Lifecycle.State.STARTED){
                            viewModel.getSearchNews(query).collectLatest{ articles ->
                                pagingNewsAdapter.submitData(articles)
                            }
                        }
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
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
    }
    private fun setupRecyclerView(){
        pagingNewsAdapter = PagingNewsAdapter()
        binding.rvSearchNews.apply {
            adapter = pagingNewsAdapter.withLoadStateFooter(NewsLoadStateAdapter())
            layoutManager = LinearLayoutManager(activity)
//            addOnScrollListener(this@SearchNewsFragment.scrollListener)
        }
    }
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