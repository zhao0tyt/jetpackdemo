package com.zzq.common.ext.util

/**
 * 判断是否为空 并传入相关操作
 */
inline fun <reified T> T?.notNull(notNullAction: (T) -> Unit, nullAction: () -> Unit = {}) {
    if (this != null) {
        notNullAction.invoke(this)
    } else {
        nullAction.invoke()
    }
}

private val intervalTime = 1000 * 60 * 60

fun Long?.shouldUpdate() = (System.currentTimeMillis() - (this ?: 0L)) > intervalTime