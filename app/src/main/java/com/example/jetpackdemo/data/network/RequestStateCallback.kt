package com.example.jetpackdemo.data.network

import com.example.jetpackdemo.data.model.IntegralResponse

interface RequestStateCallback {
    fun success()
    fun failed()
}