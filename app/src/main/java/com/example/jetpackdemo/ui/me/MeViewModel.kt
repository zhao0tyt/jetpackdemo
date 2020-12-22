package com.example.jetpackdemo.ui.me

import androidx.lifecycle.MutableLiveData
import com.example.jetpackdemo.data.bean.IntegralResponse
import com.example.jetpackdemo.data.network.RequestStateCallback
import com.example.jetpackdemo.data.repository.AppRepository
import com.example.jetpackdemo.util.ColorUtil
import com.zzq.common.base.viewmodel.BaseViewModel
import com.zzq.common.ext.request

class MeViewModel(private val repository: AppRepository)  : BaseViewModel(){

    var name = MutableLiveData<String>("请先登录~")
    var integral = MutableLiveData<String>("0")
    var info = MutableLiveData<String>("id：--　排名：--")
    var imageUrl = MutableLiveData<String>(ColorUtil.randomImage())
    var meData = MutableLiveData<IntegralResponse>()

    fun getIntegral(userId: String, callback: RequestStateCallback) {
        request(
            {
                repository.getIntegral(userId, callback).let {
                    meData.value = it
                }
            },
            {
                //Todo handle request error
            }
        )
    }
}