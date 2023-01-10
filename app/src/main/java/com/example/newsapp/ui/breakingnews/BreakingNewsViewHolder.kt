package com.example.newsapp.ui.breakingnews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.databinding.ItemArticlePreviewBinding
import com.example.newsapp.models.Article

//class BreakingNewsViewHolder(private val binding: ItemArticlePreviewBinding): RecyclerView.ViewHolder(binding.root) {
//
//    companion object {
//        fun create(parent: ViewGroup): BreakingNewsViewHolder {
//            val binding = ItemArticlePreviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//            return BreakingNewsViewHolder(binding)
//        }
//    }
//
//    fun bind(article: Article){
//        binding.apply {
//            Glide.with(context).load(article.urlToImage).into(ivArticleImage)
//            tvTitle.text = article.title
//            tvSource.text = article.source?.name
//            tvDescription.text = article.description
//            tvPublishedAt.text = article.publishedAt
//        }
//    }
//
//}