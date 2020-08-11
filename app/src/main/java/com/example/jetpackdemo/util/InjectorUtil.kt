package com.example.jetpackdemo.util

import com.example.jetpackdemo.data.network.Network
import com.example.jetpackdemo.data.repository.AppRepository
import com.example.jetpackdemo.login.viewmodel.RegisterModelFactory

object InjectorUtil {
    private fun getAppRepository() = AppRepository.getInstance(Network.getInstance())

    fun getRegisterViewModelFactory() = RegisterModelFactory(getAppRepository())

}