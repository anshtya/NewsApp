package com.example.newsapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentArticleBinding
import com.example.newsapp.databinding.FragmentSavedNewsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SavedNewsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSavedNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

//    private lateinit var newsAdapter: NewsAdapter
//
//    private val viewModel: NewsViewModel by activityViewModels()
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        setupRecyclerView()
//
//        viewLifecycleOwner.lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                viewModel.savedNews.collect{ articles ->
//                    newsAdapter.submitList(articles)
//                }
//            }
//        }
//
//        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
//            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
//            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
//        ) {
//            override fun onMove(
//                recyclerView: RecyclerView,
//                viewHolder: RecyclerView.ViewHolder,
//                target: RecyclerView.ViewHolder
//            ): Boolean {
//                return true
//            }
//
//            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//                val position = viewHolder.adapterPosition
//                val article = newsAdapter.currentList[position]
//                viewModel.deleteArticle(article)
//                Snackbar.make(view, "Article deleted successfully", Snackbar.LENGTH_SHORT).apply {
//                    setAction("Undo"){
//                        viewModel.saveArticle(article)
//                    }
//                    show()
//                }
//            }
//        }
//
//        ItemTouchHelper(itemTouchHelperCallback).apply {
//            attachToRecyclerView(rvSavedNews)
//        }
//    }
//
//    private fun setupRecyclerView(){
//        newsAdapter = NewsAdapter { article ->
//            onClick(article)
//        }
//        rvSavedNews.apply {
//            adapter = newsAdapter
//            layoutManager = LinearLayoutManager(activity)
//        }
//    }
//
//    private fun onClick(article: Article){
//        val bundle = Bundle().apply {
//            putSerializable("article", article)
//        }
//        findNavController().navigate(
//            R.id.action_savedNewsFragment_to_articleFragment,
//            bundle
//        )
//    }
}