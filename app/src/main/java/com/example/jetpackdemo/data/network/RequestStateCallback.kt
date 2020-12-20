package com.example.jetpackdemo.data.network


interface RequestStateCallback {
    fun success()
    fun failed(type: ErrorType)

    enum class ErrorType {
        HTTP,
        DEFAULT
    }
}