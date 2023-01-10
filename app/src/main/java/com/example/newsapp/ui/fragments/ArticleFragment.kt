package com.example.newsapp.ui.fragments

import androidx.fragment.app.Fragment
import com.example.newsapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleFragment : Fragment(R.layout.fragment_article) {

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