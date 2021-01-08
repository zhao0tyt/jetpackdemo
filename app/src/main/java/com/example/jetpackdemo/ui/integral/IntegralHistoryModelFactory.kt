package com.example.jetpackdemo.ui.integral

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.jetpackdemo.data.repository.AppRepository

class IntegralHistoryModelFactory(private val repository: AppRepository) :
    ViewModelProvider.NewInstanceFactory()  {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return IntegralHistoryViewModel(
            repository
        ) as T
    }
}
