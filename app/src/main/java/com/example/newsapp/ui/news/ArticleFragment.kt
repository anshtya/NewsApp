package com.example.newsapp.ui.news

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentArticleBinding
import com.example.newsapp.ui.bookmarkednews.BookmarkedNewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleFragment : Fragment() {

    private lateinit var binding: FragmentArticleBinding
    private val viewModel: BookmarkedNewsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentArticleBinding.inflate(inflater, container, false)
        return binding.root
    }

    private val args: ArticleFragmentArgs by navArgs()

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val article = args.article

        binding.apply {
            webView.apply {
                loadUrl(article.url)
                webViewClient = WebViewClient()
                settings.javaScriptEnabled = true
            }

            article.let {
                if (it.isBookmarked) {
                    fab.setImageResource(R.drawable.ic_bookmarked)
                } else {
                    fab.setImageResource(R.drawable.ic_bookmark_border)
                }
            }

            fab.setOnClickListener {
                article.let {
                    if (it.isBookmarked) {
                        viewModel.updateBookmarkStatus(it.url, isBookmarked = false)
                        fab.setImageResource(R.drawable.ic_bookmark_border)
                    } else {
                        viewModel.updateBookmarkStatus(it.url, isBookmarked = true)
                        fab.setImageResource(R.drawable.ic_bookmarked)
                    }
                }
            }
        }
    }
}