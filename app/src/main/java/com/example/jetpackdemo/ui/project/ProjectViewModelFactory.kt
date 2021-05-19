package com.example.jetpackdemo.ui.project

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.jetpackdemo.data.repository.AppRepository
import com.example.jetpackdemo.ui.square.SquareViewModel

class ProjectViewModelFactory  (private val repository: AppRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProjectViewModel(
            repository
        ) as T
    }
}