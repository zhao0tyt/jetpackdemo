package com.example.jetpackdemo.ui.integral

import androidx.lifecycle.MutableLiveData
import com.example.jetpackdemo.data.bean.IntegralHistoryResponse
import com.example.jetpackdemo.data.bean.state.ListDataUiState
import com.example.jetpackdemo.data.repository.AppRepository
import com.zzq.common.base.viewmodel.BaseViewModel
import com.zzq.common.ext.request

class IntegralHistoryViewModel(private val repository: AppRepository) : BaseViewModel()  {
    private var pageNo = 1

    var integralHistoryDataState = MutableLiveData<ListDataUiState<IntegralHistoryResponse>>()

    fun getIntegralHistoryData(isRefresh: Boolean) {
        if (isRefresh) {
            pageNo = 1
        }
        request({ repository.getIntegralHistory(pageNo) }, {
            //请求成功
            pageNo++
            val listDataUiState =
                ListDataUiState(
                    isSuccess = true,
                    isRefresh = isRefresh,
                    isEmpty = it.isEmpty(),
                    hasMore = it.hasMore(),
                    isFirstEmpty = isRefresh && it.isEmpty(),
                    listData = it.datas
                )
            integralHistoryDataState.value = listDataUiState
        }, {
            //请求失败
            val listDataUiState =
                ListDataUiState(
                    isSuccess = false,
                    errMessage = it.errorMsg,
                    isRefresh = isRefresh,
                    listData = arrayListOf<IntegralHistoryResponse>()
                )
            integralHistoryDataState.value = listDataUiState
        })
    }
}