package com.example.jetpackdemo

import android.content.Context
import com.tencent.mmkv.MMKV
import com.zzq.common.base.BaseApp

class App : BaseApp() {
    companion object {
        lateinit var instance: App
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        context = this
        MMKV.initialize(this)
    }
}