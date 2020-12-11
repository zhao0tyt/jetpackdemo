package com.example.jetpackdemo.ui.integral

import androidx.lifecycle.MutableLiveData
import com.example.jetpackdemo.data.model.IntegralResponse
import com.example.jetpackdemo.data.repository.AppRepository
import com.zzq.common.base.viewmodel.BaseViewModel


class IntegralViewModel(private val repository: AppRepository ): BaseViewModel() {

    var rank = MutableLiveData<IntegralResponse>()
}