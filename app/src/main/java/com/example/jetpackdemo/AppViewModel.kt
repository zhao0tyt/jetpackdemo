package com.example.jetpackdemo

import com.example.jetpackdemo.data.bean.UserInfo
import com.example.jetpackdemo.util.CacheUtil
import com.example.jetpackdemo.util.SettingUtil
import com.kunminx.architecture.ui.callback.UnPeekLiveData
import com.zzq.common.base.viewmodel.BaseViewModel

/**
 * 描述　:APP全局的ViewModel，可以存放公共数据，当他数据改变时，所有监听他的地方都会收到回调,也可以做发送消息
 * 比如 全局可使用的 地理位置信息，账户信息,App的基本配置等等，
 */
class AppViewModel : BaseViewModel() {

    //App的账户信息
    var userinfo = UnPeekLiveData<UserInfo>()

    //App 列表动画
    var appAnimation = UnPeekLiveData<Int>()

    init {
        //默认值保存的账户信息，没有登陆过则为null
        userinfo.value = CacheUtil.getUser()
        //初始化列表动画
        appAnimation.value = SettingUtil.getListMode()
    }
}