package com.example.jetpackdemo.util

import android.content.Context
import android.graphics.Color
import android.preference.PreferenceManager
import androidx.core.content.ContextCompat
import com.example.jetpackdemo.R
import com.tencent.mmkv.MMKV

object SettingUtil {
    /**
     * 获取列表动画模式
     */
    fun getListMode(): Int {
        val kv = MMKV.defaultMMKV()
        //0 关闭动画 1.渐显 2.缩放 3.从下到上 4.从左到右 5.从右到左
        return kv.decodeInt("mode", 2)
    }
    /**
     * 设置列表动画模式
     */
    fun setListMode(mode: Int) {
        val kv = MMKV.defaultMMKV()
        kv.encode("mode", mode)
    }
}