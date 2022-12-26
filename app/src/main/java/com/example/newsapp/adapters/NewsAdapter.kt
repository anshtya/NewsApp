package com.example.newsapp.adapters

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

    inner class ArticleViewHolder(itemView: View, val onClick: (Article) -> Unit) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(article: Article){
            itemView.apply {
                Glide.with(this).load(article.urlToImage).into(ivArticleImage)
                tvTitle.text = article.title
                tvSource.text = article.source?.name
                tvDescription.text = article.description
                tvPublishedAt.text = article.publishedAt
                itemView.setOnClickListener {
                    onClick(article)
                }
            }
        }
    }

    companion object{
        private val DifferCallback = object: DiffUtil.ItemCallback<Article>(){
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.url == newItem.url
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
            ),
            onClick
        )
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = currentList[position]
        holder.bind(article)
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

}