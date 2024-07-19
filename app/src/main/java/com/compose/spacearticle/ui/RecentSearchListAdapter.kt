package com.compose.spacearticle.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.compose.spacearticle.R
import com.compose.spacearticle.databinding.ArticleItemBinding
import com.compose.spacearticle.domain.model.Article

class RecentSearchListAdapter : ListAdapter<Article, RecentSearchListAdapter.ViewHolder>(
    DIFF_CALLBACK
) {

    var onItemClick: ((Article) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ArticleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        data?.let {
            holder.bind(it)
        }
    }

    class ViewHolder(
        private val binding: ArticleItemBinding,
        private val onItemClick: ((Article) -> Unit)?
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Article) {

            binding.apply {
                tvTitle.text = data.title
                tvNewsite.text = data.newsSite
                Glide.with(itemView.context)
                    .load(data.imageUrl)
                    .placeholder(R.drawable.image_not_found)
                    .into(imageView)
                imageView.contentDescription = data.title
            }

            binding.root.setOnClickListener {
                onItemClick?.invoke(data)
            }

        }

    }

    override fun getItemViewType(position: Int): Int {
        return if (position < itemCount) {
            VIEW_TYPE_MOVIE
        } else {
            VIEW_TYPE_LOADING
        }
    }

    companion object {
        const val VIEW_TYPE_MOVIE = 1
        const val VIEW_TYPE_LOADING = 2

        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }
        }
    }
}