package com.example.jetpackdemo.ui.login.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.jetpackdemo.data.repository.AppRepository
import com.zzq.common.base.viewmodel.BaseViewModel
import com.zzq.common.ext.request
import com.zzq.common.state.ResultState

class RegisterViewModel(private val repository: AppRepository) : BaseViewModel() {

    var registerResult = MutableLiveData<ResultState<Any>>()

    fun register(username: String, password: String, repassword: String) {
        request(
            { repository.register(username, password, repassword) },
            registerResult,
            true,
            "正在注册中..."
        )
    }
}
