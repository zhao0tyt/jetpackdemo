package com.example.jetpackdemo.ui.base

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.example.jetpackdemo.AppViewModel
import com.example.jetpackdemo.ext.dismissLoadingExt
import com.example.jetpackdemo.ext.hideSoftKeyboard
import com.example.jetpackdemo.ext.showLoadingExt
import com.zzq.common.base.fragment.BaseVmDbFragment
import com.zzq.common.base.viewmodel.BaseViewModel
import com.zzq.common.ext.getAppViewModel

abstract class BaseFragment<VM : BaseViewModel, DB : ViewDataBinding> : BaseVmDbFragment<VM, DB>()  {

    //Application全局的ViewModel，里面存放了一些账户信息，基本配置信息等
    val appViewModel: AppViewModel by lazy { getAppViewModel<AppViewModel>() }

    /**
     * 当前Fragment绑定的视图布局
     */
    abstract override fun layoutId(): Int


    abstract override fun initView(savedInstanceState: Bundle?)

    /**
     * 懒加载 只有当前fragment视图显示时才会触发该方法
     */
    override fun lazyLoadData() {}

    /**
     * 创建LiveData观察者 Fragment执行onViewCreated后触发
     */
    override fun createObserver() {}

    /**
     * Fragment执行onViewCreated后触发
     */
    override fun initData() {

    }

    /**
     * 打开等待框
     */
    override fun showLoading(message: String) {
//        创建dialog并显示
        showLoadingExt(message)
    }

    /**
     * 关闭等待框
     */
    override fun dismissLoading() {
        //关闭dialog
        dismissLoadingExt()
    }

    override fun onPause() {
        super.onPause()
        hideSoftKeyboard(activity)
    }
}