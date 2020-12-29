package com.example.jetpackdemo.ui.officialaccount

import androidx.lifecycle.MutableLiveData
import com.example.jetpackdemo.data.bean.ClassifyResponse
import com.example.jetpackdemo.data.repository.AppRepository
import com.zzq.common.base.viewmodel.BaseViewModel
import com.zzq.common.ext.requestNoCheck
import com.zzq.common.state.ResultState

class OfficialAccountViewModel(private val repository: AppRepository): BaseViewModel() {

    var titleData: MutableLiveData<ResultState<ArrayList<ClassifyResponse>>> = MutableLiveData()

    fun getOfficialAccountTitle(){
        requestNoCheck({repository.getOfficialAccountTitle()}, titleData)
    }

}