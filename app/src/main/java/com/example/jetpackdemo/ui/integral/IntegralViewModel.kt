package com.example.jetpackdemo.ui.integral

import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.jetpackdemo.data.bean.IntegralResponse
import com.example.jetpackdemo.data.bean.state.ListDataUiState
import com.example.jetpackdemo.data.repository.AppRepository
import com.zzq.common.base.viewmodel.BaseViewModel
import com.zzq.common.ext.request
import com.zzq.common.util.LogUtil


class IntegralViewModel(private val repository: AppRepository) : BaseViewModel() {

    private var pageNo = 1

    var rank = MutableLiveData<IntegralResponse>()
    //积分排行数据
    var integralDataState = MutableLiveData<ListDataUiState<IntegralResponse>>()

    /**
     * 成功返回true,失败返回false
     */
    fun getIntegralRank(isRefresh: Boolean){
        if (isRefresh) {
            pageNo = 1
        }
        LogUtil.logd("page = "+pageNo)
        request({repository.getIntegralRank(pageNo)},{
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
            integralDataState.value = listDataUiState
        }, {
            //请求失败
            val listDataUiState =
                ListDataUiState(
                    isSuccess = false,
                    errMessage = it.errorMsg,
                    isRefresh = isRefresh,
                    listData = arrayListOf<IntegralResponse>()
                )
            integralDataState.value = listDataUiState
        })
    }

//    fun getIntegralRankPager() = Pager(PagingConfig(pageSize = 20)) {
//        IntegralPagingSource(appRepository = repository)
//    }.flow
}