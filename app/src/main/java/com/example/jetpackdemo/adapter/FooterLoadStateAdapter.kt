package com.example.jetpackdemo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.jetpackdemo.R
import com.example.jetpackdemo.databinding.RecyclerviewFooterBinding
import com.example.jetpackdemo.ui.integral.IntegralAdapter

class FooterLoadStateAdapter(private val adapter: IntegralAdapter): LoadStateAdapter<FootLoadStateViewHolder>(){
    override fun onBindViewHolder(holder: FootLoadStateViewHolder, loadState: LoadState) {
        holder.bindTo(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): FootLoadStateViewHolder {
        return FootLoadStateViewHolder(parent) { adapter.retry() }
    }

}

class FootLoadStateViewHolder(parent: ViewGroup, private val retryCallback: () -> Unit) : RecyclerView.ViewHolder
    (LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_footer, parent, false)) {
    private val binding = RecyclerviewFooterBinding.bind(itemView)
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