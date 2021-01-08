package com.example.jetpackdemo

import android.content.Context
import com.example.jetpackdemo.widget.EmptyCallback
import com.example.jetpackdemo.widget.ErrorCallback
import com.example.jetpackdemo.widget.LoadingCallback
import com.kingja.loadsir.callback.SuccessCallback
import com.kingja.loadsir.core.LoadSir
import com.tencent.mmkv.MMKV
import com.xuexiang.xui.XUI
import com.zzq.common.base.BaseApp

class App : BaseApp() {
    companion object {
        lateinit var instance: App
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        XUI.init(this)
        instance = this
        context = this
        MMKV.initialize(this)
        //界面加载管理 初始化
        LoadSir.beginBuilder()
            .addCallback(LoadingCallback())//加载
            .addCallback(ErrorCallback())//错误
            .addCallback(EmptyCallback())//空
            .setDefaultCallback(SuccessCallback::class.java)//设置默认加载状态页
            .commit()
    }
}