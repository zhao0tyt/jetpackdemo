package com.example.jetpackdemo.ui.login.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackdemo.App
import com.example.jetpackdemo.data.model.ApiResponse
import com.example.jetpackdemo.data.model.Register
import com.example.jetpackdemo.data.model.UserInfo
import com.example.jetpackdemo.data.repository.AppRepository
import com.zzq.common.base.viewmodel.BaseViewModel
import com.zzq.common.ext.request
import com.zzq.common.state.ResultState
import kotlinx.coroutines.launch

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
