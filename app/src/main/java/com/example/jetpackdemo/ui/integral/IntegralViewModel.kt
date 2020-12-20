package com.example.jetpackdemo.ui.integral

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.jetpackdemo.data.model.ApiPagerResponse
import com.example.jetpackdemo.data.model.IntegralResponse
import com.example.jetpackdemo.data.repository.AppRepository
import com.zzq.common.base.viewmodel.BaseViewModel
import com.zzq.common.ext.request
import com.zzq.common.state.ResultState


class IntegralViewModel(private val repository: AppRepository ): BaseViewModel() {

    var rank = MutableLiveData<IntegralResponse>()

    fun getIntegralRankPager()= Pager(PagingConfig(pageSize = 20)) {
        IntegralPagingSource(appRepository = repository)
    }.flow

}