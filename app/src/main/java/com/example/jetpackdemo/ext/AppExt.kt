package com.example.jetpackdemo.ext

import android.util.Log
import androidx.navigation.NavController
import com.example.jetpackdemo.R
import com.example.jetpackdemo.util.CacheUtil
import com.zzq.common.ext.navigateAction

/**
 * 拦截登录操作，如果没有登录跳转登录，登录过了贼执行你的方法
 */
fun NavController.jumpByLogin(action: (NavController) -> Unit) {

    if (CacheUtil.isLogin()) {
        action(this)
    } else {
        this.navigateAction(R.id.action_to_loginFragment)
    }
}