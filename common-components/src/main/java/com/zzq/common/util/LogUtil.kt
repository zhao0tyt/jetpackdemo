package com.zzq.common.util

import android.text.TextUtils
import android.util.Log


/**
 * 作者　: hegaojian
 * 时间　: 2020/3/26
 * 描述　:
 */
object LogUtil {
    private const val TAG = "JetpackDemo"
    var IS_DEBUG = true

    private enum class LEVEL {
        V, D, I, W, E
    }

    fun logv(tag: String, message: String) = log(LEVEL.V, tag, message)
    fun logv(message: String) = log(LEVEL.V, TAG, message)

    fun logd(tag: String = TAG, message: String) = log(LEVEL.D, tag, message)
    fun logd(message: String) = log(LEVEL.D, TAG, message)

    fun logi(tag: String = TAG, message: String) = log(LEVEL.I, tag, message)
    fun logi(message: String) = log(LEVEL.I, TAG, message)

    fun logw(tag: String = TAG, message: String) = log(LEVEL.W, tag, message)
    fun logw(message: String) = log(LEVEL.W, TAG, message)

    fun loge(tag: String = TAG, message: String) = log(LEVEL.E, tag, message)
    fun loge(message: String) = log(LEVEL.E, TAG, message)

    private fun log(level: LEVEL, tag: String, message: String) {
        if (!IS_DEBUG) return
        when (level) {
            LEVEL.V -> Log.v(tag, message)
            LEVEL.D -> Log.d(tag, message)
            LEVEL.I -> Log.i(tag, message)
            LEVEL.W -> Log.w(tag, message)
            LEVEL.E -> Log.e(tag, message)
        }
    }









    fun debugInfo(tag: String?, msg: String) {
        if (!IS_DEBUG || TextUtils.isEmpty(msg)) {
            return
        }
        Log.d(tag, msg)
    }

    fun debugInfo(msg: String) {
        debugInfo(
            TAG,
            msg
        )
    }

    fun warnInfo(tag: String?, msg: String) {
        if (!IS_DEBUG || TextUtils.isEmpty(msg)) {
            return
        }
        Log.w(tag, msg)
    }

    fun warnInfo(msg: String) {
        warnInfo(
            TAG,
            msg
        )
    }

    /**
     * 这里使用自己分节的方式来输出足够长度的 message
     *
     * @param tag 标签
     * @param msg 日志内容
     */
    fun debugLongInfo(tag: String?, msg: String) {
        var msg = msg
        if (!IS_DEBUG || TextUtils.isEmpty(msg)) {
            return
        }
        msg = msg.trim { it <= ' ' }
        var index = 0
        val maxLength = 3500
        var sub: String
        while (index < msg.length) {
            sub = if (msg.length <= index + maxLength) {
                msg.substring(index)
            } else {
                msg.substring(index, index + maxLength)
            }
            index += maxLength
            Log.d(tag, sub.trim { it <= ' ' })
        }
    }

    fun debugLongInfo(msg: String) {
        debugLongInfo(
            TAG,
            msg
        )
    }

}