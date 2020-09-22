package com.example.jetpackdemo.util

import android.text.TextUtils
import com.example.jetpackdemo.data.model.UserInfo
import com.google.gson.Gson
import com.tencent.mmkv.MMKV

object CacheUtil {
    /**
     * 获取保存的账户信息
     */
    fun getUser(): UserInfo? {
        val kv = MMKV.defaultMMKV()
        val userStr = kv.decodeString("user")
        return if (TextUtils.isEmpty(userStr)) {
            null
        } else {
            Gson().fromJson(userStr, UserInfo::class.java)
        }
    }

    /**
     * 设置账户信息
     */
    fun setUser(userResponse: UserInfo?) {
        val kv = MMKV.defaultMMKV()
        if (userResponse == null) {
            kv.encode("user", "")
//            setIsLogin(false)
        } else {
            kv.encode("user", Gson().toJson(userResponse))
//            setIsLogin(true)
        }

    }

}