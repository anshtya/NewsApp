package com.example.newsapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.models.Article
import kotlinx.android.synthetic.main.item_article_preview.view.*

class NewsAdapter(private val onClick: (Article) -> Unit):
    ListAdapter<Article, NewsAdapter.ArticleViewHolder>(DifferCallback) {

    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var currentArticle: Article? = null

        init {
            itemView.setOnClickListener {
                currentArticle?.let{ article ->
                    onClick(article)
                }
            }
        }

        fun bind(article: Article){
            currentArticle = article
            itemView.apply {
                Glide.with(this).load(article.urlToImage).into(ivArticleImage)
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
        return ArticleViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_article_preview,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = getItem(position)
        if (article != null) {
            holder.bind(article)
        }
    }

}