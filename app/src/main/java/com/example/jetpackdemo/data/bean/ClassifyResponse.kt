package com.example.jetpackdemo.data.bean

import android.annotation.SuppressLint
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

/**
 * 项目分类
 */
@SuppressLint("ParcelCreator")
@Parcelize
data class ClassifyResponse(var children: List<String> = listOf(),
                            var courseId: Int = 0,
                            var id: Int = 0,
                            var name: String = "",
                            var order: Int = 0,
                            var parentChapterId: Int = 0,
                            var userControlSetTop: Boolean = false,
                            var visible: Int = 0,
                            //后添加为了计算更新间隔时间
                            var mLastTime: Long) : Parcelable
