package com.example.jetpackdemo.ui.officialaccount

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.jetpackdemo.data.repository.AppRepository

class OfficialAccountModelFactory(private val repository: AppRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return OfficialAccountViewModel(
            repository
        ) as T
    }
}