package com.example.jetpackdemo.ui.integral

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

class HeadLoadStateAdapter(private val adapter: IntegralAdapter): LoadStateAdapter<HeadLoadStateViewHolder>(){
    override fun onBindViewHolder(holder: HeadLoadStateViewHolder, loadState: LoadState) {
        holder.bindTo(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): HeadLoadStateViewHolder {
        return HeadLoadStateViewHolder(parent){ adapter.retry() }
    }

}