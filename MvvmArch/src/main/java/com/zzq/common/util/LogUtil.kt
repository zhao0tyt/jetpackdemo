package com.zzq.common.util

import android.util.Log


/**
 * 作者　: hegaojian
 * 时间　: 2020/3/26
 * 描述　:
 */
object LogUtil {
    private const val TAG = "zzq"
    var IS_DEBUG = true

    private enum class LEVEL {
        V, D, I, W, E
    }

    fun logv(tag: String, message: String) = log(LEVEL.V, tag, message)
    fun logv(message: String) = log(LEVEL.V, TAG, makeLogStringWithLongInfo(message))

    fun logd(tag: String = TAG, message: String) = log(LEVEL.D, tag, message)
    fun logd(message: String) = log(LEVEL.D, TAG, makeLogStringWithLongInfo(message))

    fun logi(tag: String = TAG, message: String) = log(LEVEL.I, tag, message)
    fun logi(message: String) = log(LEVEL.I, TAG, makeLogStringWithLongInfo(message))

    fun logw(tag: String = TAG, message: String) = log(LEVEL.W, tag, message)
    fun logw(message: String) = log(LEVEL.W, TAG, makeLogStringWithLongInfo(message))

    fun loge(tag: String = TAG, message: String) = log(LEVEL.E, tag, message)
    fun loge(message: String) = log(LEVEL.E, TAG, makeLogStringWithLongInfo(message))

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

    private fun makeLogStringWithLongInfo(vararg message: String): String {
        val stackTrace =
            Thread.currentThread().stackTrace[4]
        val builder = StringBuilder()
        appendTag(builder, stackTrace)
        appendTraceInfo(builder, stackTrace)
        for (i in message) {
            builder.append(i)
        }
        return builder.toString()
    }

    private fun appendTag(
        builder: StringBuilder,
        stackTrace: StackTraceElement
    ) {
        builder.append('[')
        builder.append(suppressFileExtension(stackTrace.fileName))
        builder.append("] ")
    }

    private fun appendTraceInfo(
        builder: StringBuilder,
        stackTrace: StackTraceElement
    ) {
        builder.append(stackTrace.methodName)
        builder.append(":")
        builder.append(stackTrace.lineNumber)
        builder.append(" ")
    }

    private fun suppressFileExtension(filename: String): String {
        val extensionPosition = filename.lastIndexOf('.')
        return if (extensionPosition > 0 && extensionPosition < filename.length) {
            filename.substring(0, extensionPosition)
        } else {
            filename
        }
    }
}