package com.example.tricholog.ui.dashboard.articles

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tricholog.databinding.ItemArticleSummaryBinding
import com.example.tricholog.ui.dashboard.articles.model.ArticleUi

class ArticlesDiffUtil : DiffUtil.ItemCallback<ArticleUi>() {
    override fun areItemsTheSame(oldItem: ArticleUi, newItem: ArticleUi): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ArticleUi, newItem: ArticleUi): Boolean {
        return oldItem == newItem
    }
}

class ArticlesAdapter(
    private val onReadClick: (id: String) -> Unit
) :
    ListAdapter<ArticleUi, ArticlesAdapter.ArticleViewHolder>(ArticlesDiffUtil()) {

    inner class ArticleViewHolder(private val binding: ItemArticleSummaryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(article: ArticleUi) {
            binding.apply {
                tvTitle.text = article.title
                tvAuthor.text = article.author
                tvSummary.text = article.summary
                tvReadTimeMin.text = article.readTimeMin.toString()

                btnRead.setOnClickListener { onReadClick(article.id) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding: ItemArticleSummaryBinding = ItemArticleSummaryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = getItem(position)
        holder.onBind(article)
    }
}