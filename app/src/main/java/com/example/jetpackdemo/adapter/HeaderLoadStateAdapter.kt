package com.example.jetpackdemo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.jetpackdemo.R
import com.example.jetpackdemo.databinding.RecyclerviewHeaderBinding

class HeaderLoadStateAdapter: LoadStateAdapter<HeadLoadStateViewHolder>(){
    override fun onBindViewHolder(holder: HeadLoadStateViewHolder, loadState: LoadState) {
        holder.bindTo(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): HeadLoadStateViewHolder {
        return HeadLoadStateViewHolder(parent)
    }

}

class HeadLoadStateViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder
    (LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_header, parent, false)) {
    private val binding = RecyclerviewHeaderBinding.bind(itemView)
        private val successMsg = binding.tvRvHeader

    fun bindTo(loadState: LoadState) {
        successMsg.isVisible = loadState is LoadState.Loading
    }
}