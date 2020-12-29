package com.example.jetpackdemo.util

import android.content.Context
import com.example.jetpackdemo.data.dao.AppDatabase
import com.example.jetpackdemo.data.network.Network
import com.example.jetpackdemo.data.repository.AppRepository
import com.example.jetpackdemo.ui.integral.IntegralModelFactory
import com.example.jetpackdemo.ui.login.viewmodel.LoginModelFactory
import com.example.jetpackdemo.ui.login.viewmodel.RegisterModelFactory
import com.example.jetpackdemo.ui.me.MeModelFactory
import com.example.jetpackdemo.ui.officialaccount.OfficialAccountModelFactory

object InjectorUtil {
    private fun getAppRepository(context: Context) = AppRepository.getInstance(AppDatabase.getDatabase(context), Network.getInstance())

    fun getRegisterViewModelFactory(context: Context) = RegisterModelFactory(getAppRepository(context))

    fun getLoginViewModelFactory(context: Context) = LoginModelFactory(getAppRepository(context))

    fun getMeViewModelFactory(context: Context) = MeModelFactory(getAppRepository(context))

    fun getIntegralViewModelFactory(context: Context) = IntegralModelFactory(getAppRepository(context))

    fun getOfficialAccountViewModelFactory(context: Context) = OfficialAccountModelFactory(getAppRepository(context))
}