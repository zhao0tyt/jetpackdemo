package com.example.jetpackdemo.ui.collect

import androidx.lifecycle.MutableLiveData
import com.example.jetpackdemo.data.bean.state.CollectUiState
import com.example.jetpackdemo.data.repository.AppRepository
import com.zzq.common.base.viewmodel.BaseViewModel
import com.zzq.common.ext.request

class CollectViewModel(private val repository: AppRepository) : BaseViewModel() {

    //收藏文章
    val collectUiState: MutableLiveData<CollectUiState> = MutableLiveData()

    /**
     * 收藏 文章
     * 提醒一下，玩安卓的收藏 和取消收藏 成功后返回的data值为null，所以在CollectRepository中的返回值一定要加上 非空？
     * 不然会出错
     */
    fun collect(id: Int) {
        request({ repository.collect(id) }, {
            val uiState = CollectUiState(isSuccess = true, collect = true, id = id)
            collectUiState.value = uiState
        }, {
            val uiState = CollectUiState(isSuccess = false, collect = false, errorMsg = it.errorMsg, id = id)
            collectUiState.value = uiState
        })
    }

    /**
     * 取消收藏文章
     * 提醒一下，玩安卓的收藏 和取消收藏 成功后返回的data值为null，所以在CollectRepository中的返回值一定要加上 非空？
     * 不然会出错
     */
    fun uncollect(id: Int) {
        request({ repository.unCollect(id) }, {
            val uiState = CollectUiState(isSuccess = true, collect = false, id = id)
            collectUiState.value = uiState
        }, {
            val uiState =
                CollectUiState(isSuccess = false, collect = true, errorMsg = it.errorMsg, id = id)
            collectUiState.value = uiState
        })
    }
}