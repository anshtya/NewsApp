package com.example.newsapp.ui.breakingnews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentBreakingNewsBinding
import com.example.newsapp.data.network.model.Article
import com.example.newsapp.viewmodel.NewsViewModel
import com.example.newsapp.ui.adapters.NewsLoadStateAdapter
import com.example.newsapp.ui.adapters.PagingNewsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BreakingNewsFragment : Fragment() {

    private val viewModel: NewsViewModel by activityViewModels()
    private lateinit var binding: FragmentBreakingNewsBinding
    private lateinit var pagingNewsAdapter: PagingNewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBreakingNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        binding.btRetry.setOnClickListener { pagingNewsAdapter.retry() }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.breakingNews.collect { articles ->
                    pagingNewsAdapter.submitData(articles)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            lifecycleScope.launch {
                pagingNewsAdapter.loadStateFlow.collect { newsLoadState ->
                    when (newsLoadState.source.refresh) {
                        is LoadState.Error -> {
                            binding.apply {
                                progressBar.visibility = View.INVISIBLE
                                btRetry.visibility = View.VISIBLE
                                tvError.visibility = View.VISIBLE
                            }
                        }
                        is LoadState.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is LoadState.NotLoading -> {
                            binding.apply {
                                progressBar.visibility = View.INVISIBLE
                                btRetry.visibility = View.INVISIBLE
                                tvError.visibility = View.INVISIBLE
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        pagingNewsAdapter = PagingNewsAdapter { article ->
            onClick(article)
        }
        binding.rvBreakingNews.apply {
            adapter = pagingNewsAdapter.withLoadStateFooter(NewsLoadStateAdapter())
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun onClick(article: Article) {
        val bundle = Bundle().apply {
            putSerializable("article", article)
        }
        findNavController().navigate(
            R.id.action_breakingNewsFragment_to_articleFragment,
            bundle
        )
    }
}