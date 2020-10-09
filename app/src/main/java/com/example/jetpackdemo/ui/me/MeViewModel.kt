package com.example.jetpackdemo.ui.me

import androidx.lifecycle.MutableLiveData
import com.example.jetpackdemo.util.ColorUtil
import com.zzq.common.base.viewmodel.BaseViewModel

class MeViewModel : BaseViewModel(){

    var name = MutableLiveData<String>()
    var integral = MutableLiveData<Int>()
    var info = MutableLiveData<String>()
    var imageUrl = MutableLiveData<String>(ColorUtil.randomImage())

}