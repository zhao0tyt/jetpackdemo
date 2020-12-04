package com.example.jetpackdemo.ui.me

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.jetpackdemo.data.repository.AppRepository
import com.example.jetpackdemo.ui.login.viewmodel.LoginViewModel

class MeModelFactory(private val repository: AppRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MeViewModel(
            repository
        ) as T
    }
}