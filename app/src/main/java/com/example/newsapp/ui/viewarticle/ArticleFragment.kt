package com.example.newsapp.ui.viewarticle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.newsapp.databinding.FragmentArticleBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleFragment : Fragment() {

    private lateinit var binding: FragmentArticleBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentArticleBinding.inflate(inflater, container, false)
        return binding.root
    }

//    private val viewModel: NewsViewModel by activityViewModels()
//
//    private val args: ArticleFragmentArgs by navArgs()
//
//    @SuppressLint("SetJavaScriptEnabled")
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//
//        val article = args.article
//        webView.apply {
//            article.url?.let { loadUrl(it) }
//            webViewClient = WebViewClient()
//            settings.javaScriptEnabled = true
//        }
//
//        fab.setOnClickListener {
//            viewModel.saveArticle(article)
//            Snackbar.make(view, "Article saved successfully", Snackbar.LENGTH_SHORT).show()
//        }
//    }
}