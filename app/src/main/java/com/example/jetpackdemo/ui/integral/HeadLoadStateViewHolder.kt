package com.example.jetpackdemo.ui.integral

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.example.jetpackdemo.R
import com.example.jetpackdemo.databinding.RecyclerviewHeaderBinding

class HeadLoadStateViewHolder(parent: ViewGroup,private val retryCallback: () -> Unit) : RecyclerView.ViewHolder
    (LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_header, parent, false)) {
    private val binding = RecyclerviewHeaderBinding.bind(itemView)
//    private val successMsg = binding.tvRvHeader
//
//    fun bindTo(loadState: LoadState) {
//        successMsg.isVisible = loadState is LoadState.Loading
//    }
private val progressBar = binding.progressBar
    private val errorMsg = binding.errorMsg
    private val retry = binding.retryButton
        .also {
            it.setOnClickListener { retryCallback() }
        }

    fun bindTo(loadState: LoadState) {
        progressBar.isVisible = loadState is LoadState.Loading
        retry.isVisible = loadState is LoadState.Error
        errorMsg.isVisible = !(loadState as? LoadState.Error)?.error?.message.isNullOrBlank()
        errorMsg.text = (loadState as? LoadState.Error)?.error?.message
    }

}