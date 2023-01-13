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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.databinding.FragmentBreakingNewsBinding
import com.example.newsapp.ui.NewsViewModel
import com.example.newsapp.ui.NewsLoadStateAdapter
import com.example.newsapp.ui.PagingNewsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
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

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.breakingNews.collectLatest { articles ->
                    pagingNewsAdapter.submitData(articles)
                }
            }
        }
    }
    private fun setupRecyclerView(){
        pagingNewsAdapter = PagingNewsAdapter()
        binding.rvBreakingNews.apply {
            adapter = pagingNewsAdapter.withLoadStateFooter(NewsLoadStateAdapter())
            layoutManager = LinearLayoutManager(activity)
//            addOnScrollListener(this@BreakingNewsFragment.scrollListener)
        }
    }

//    private fun onClick(article: Article){
//        val bundle = Bundle().apply {
//            putSerializable("article", article)
//        }
//        findNavController().navigate(
//            R.id.action_breakingNewsFragment_to_articleFragment,
//            bundle
//        )
//    }
}