package com.example.jetpackdemo

import android.app.Application
import android.content.Context

open class BaseApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        context = this
    }

    companion object {
        lateinit var context: Context
    }
}