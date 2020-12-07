package com.example.jetpackdemo.ui.integral

import androidx.databinding.ObservableField
import com.example.jetpackdemo.data.model.Integral
import com.example.jetpackdemo.data.repository.AppRepository
import com.zzq.common.base.viewmodel.BaseViewModel


class IntegralViewModel(private val repository: AppRepository ): BaseViewModel() {

    var rank = ObservableField<Integral>()
}