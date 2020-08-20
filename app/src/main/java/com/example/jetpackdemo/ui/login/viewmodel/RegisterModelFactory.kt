package com.example.jetpackdemo.ui.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.jetpackdemo.data.repository.AppRepository

class RegisterModelFactory(private val repository: AppRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RegisterViewModel(
            repository
        ) as T
    }
}