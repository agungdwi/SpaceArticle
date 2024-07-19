package com.compose.spacearticle.ui

import com.compose.spacearticle.databinding.SiteItemBinding


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class NewsSiteAdapter(private val newsSites: Array<String>) : RecyclerView.Adapter<NewsSiteAdapter.ViewHolder>() {

    var onItemClick: ((String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = SiteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val newsSite = newsSites[position]
        holder.bind(newsSite)
    }

    override fun getItemCount(): Int {
        return newsSites.size
    }

    inner class ViewHolder(private val binding: SiteItemBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(newsSites[adapterPosition])
            }
        }

        fun bind(newsSite: String) {
            binding.textView.text = newsSite
        }
    }
}