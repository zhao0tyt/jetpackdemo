package com.example.jetpackdemo.ui.integral

import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.jetpackdemo.data.bean.IntegralResponse
import com.example.jetpackdemo.data.repository.AppRepository
import com.zzq.common.base.viewmodel.BaseViewModel


class IntegralViewModel(private val repository: AppRepository ): BaseViewModel() {

    var rank = MutableLiveData<IntegralResponse>()

    fun getIntegralRankPager()= Pager(PagingConfig(pageSize = 20)) {
        IntegralPagingSource(appRepository = repository)
    }.flow

}