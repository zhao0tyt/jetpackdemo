package com.example.jetpackdemo.util

import com.example.jetpackdemo.data.network.Network
import com.example.jetpackdemo.data.repository.AppRepository
import com.example.jetpackdemo.ui.login.viewmodel.LoginModelFactory
import com.example.jetpackdemo.ui.login.viewmodel.RegisterModelFactory
import com.example.jetpackdemo.ui.me.MeModelFactory

object InjectorUtil {
    private fun getAppRepository() = AppRepository.getInstance(Network.getInstance())

    fun getRegisterViewModelFactory() =
        RegisterModelFactory(
            getAppRepository()
        )
    fun getLoginViewModelFactory() =
        LoginModelFactory(
            getAppRepository()
        )
    fun getMeViewModelFactory() =
        MeModelFactory(
            getAppRepository()
        )
}