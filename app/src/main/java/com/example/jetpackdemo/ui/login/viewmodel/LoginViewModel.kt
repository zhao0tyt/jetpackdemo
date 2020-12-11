package com.example.jetpackdemo.ui.login.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.jetpackdemo.data.model.UserInfo
import com.example.jetpackdemo.data.repository.AppRepository
import com.zzq.common.base.viewmodel.BaseViewModel
import com.zzq.common.ext.request
import com.zzq.common.state.ResultState

class LoginViewModel(private val repository: AppRepository) : BaseViewModel(){
    var loginResult = MutableLiveData<ResultState<UserInfo>>()

    fun login(username: String, password: String) {
        request(
            { repository.login(username, password) },
            loginResult,
            true,
            "正在登录中..."
        )
    }

}
