package com.example.jetpackdemo.data.model

import android.annotation.SuppressLint
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

/**
 * 积分
 */
@SuppressLint("ParcelCreator")
@Parcelize
@Entity(tableName = "Integral")
data class IntegralResponse(
    var coinCount: String,//当前积分
    var rank: String,
    @PrimaryKey
    var userId: String,
    var username: String,
    //后添加为了计算更新间隔时间
    var mLastTime: Long
) : Parcelable


