package com.example.jetpackdemo.ui.collect

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.jetpackdemo.data.repository.AppRepository

class CollectModelFactory(private val repository: AppRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CollectViewModel(
            repository
        ) as T
    }
}