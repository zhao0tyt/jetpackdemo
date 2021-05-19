package com.example.jetpackdemo.data.bean

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class NavigationResponse(var articles: ArrayList<AriticleResponse>,
                              var cid: Int,
                              var name: String) : Parcelable