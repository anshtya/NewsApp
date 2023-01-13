package com.example.newsapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.databinding.ItemArticlePreviewBinding
import com.example.newsapp.models.Article

class PagingNewsAdapter:
    PagingDataAdapter<Article, PagingNewsAdapter.ArticleViewHolder>(DifferCallback) {

    inner class ArticleViewHolder(private val binding: ItemArticlePreviewBinding) : RecyclerView.ViewHolder(binding.root) {

        private var currentArticle: Article? = null

//        init {
//            itemView.setOnClickListener {
//                currentArticle?.let{ article ->
//                    onClick(article)
//                }
//            }
//        }

        fun bind(article: Article){
            currentArticle = article
            binding.apply {
                Glide.with(itemView.context).load(article.urlToImage).into(ivArticleImage)
                tvTitle.text = article.title
                tvSource.text = article.source?.name
                tvDescription.text = article.description
                tvPublishedAt.text = article.publishedAt
            }
        }
    }

    companion object{
        private val DifferCallback = object: DiffUtil.ItemCallback<Article>(){
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ItemArticlePreviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = getItem(position)
        if (article != null) {
            holder.bind(article)
        }
    }

}