package com.example.jetpackdemo.ui.officialaccount

import androidx.lifecycle.MutableLiveData
import com.example.jetpackdemo.data.bean.AriticleResponse
import com.example.jetpackdemo.data.bean.ClassifyResponse
import com.example.jetpackdemo.data.bean.state.ListDataUiState
import com.example.jetpackdemo.data.repository.AppRepository
import com.zzq.common.base.viewmodel.BaseViewModel
import com.zzq.common.ext.request
import com.zzq.common.ext.requestNoCheck
import com.zzq.common.state.ResultState

class OfficialAccountViewModel(private val repository: AppRepository): BaseViewModel() {

    var pageNo = 1

    var titleData: MutableLiveData<ResultState<ArrayList<ClassifyResponse>>> = MutableLiveData()

    var publicDataState: MutableLiveData<ListDataUiState<AriticleResponse>> = MutableLiveData()

    fun getOfficialAccountTitle(){
        requestNoCheck({repository.getOfficialAccountTitle()}, titleData)
    }

    fun getPublicData(isRefresh: Boolean, cid: Int) {
        if (isRefresh) {
            pageNo = 1
        }
        request({ repository.getPublicDataFromNet(pageNo, cid) }, {
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
            publicDataState.value = listDataUiState
        }, {
            //请求失败
            val listDataUiState =
                ListDataUiState(
                    isSuccess = false,
                    errMessage = it.errorMsg,
                    isRefresh = isRefresh,
                    listData = arrayListOf<AriticleResponse>()
                )
            publicDataState.value = listDataUiState
        })
    }
}